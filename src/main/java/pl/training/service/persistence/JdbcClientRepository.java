package pl.training.service.persistence;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import pl.training.domain.Address;
import pl.training.domain.Client;

public class JdbcClientRepository implements ClientRepository {

    private static final String INSERT_CLIENT = "insert into clients values(null,:firstName,:lastName)";
    private static final String INSERT_ADDRESS = "insert into addresses values(null,:active,:type,:city,:house,:street,:zipcode,:client_id)";    

    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public JdbcClientRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Override
    public Client save(Client client) {
        Client savedClient = saveClient(client);
        savedClient.getAddresses().forEach((address) -> {
            saveAddress(address, savedClient.getId());            
        });
        return client;
    }
    
    private Client saveClient(Client client) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_CLIENT, new BeanPropertySqlParameterSource(client), keyHolder);
        client.setId(keyHolder.getKey().longValue());
        return client;
    }
    
    private Address saveAddress(Address address, Long clientId) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_ADDRESS, new MapSqlParameterSource()
                .addValue("active", address.isActive())
                .addValue("type", address.getAddressType().name())
                .addValue("city", address.getCity())
                .addValue("house", address.getHouse())
                .addValue("street", address.getStreet())
                .addValue("zipcode", address.getZipcode())
                .addValue("client_id", clientId),
                keyHolder);
        address.setId(keyHolder.getKey().longValue());
        return address;
    }
}
