package es.santander.darwin.reactivecontroller.repository;

import es.santander.darwin.reactivecontroller.model.Client;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;


@Repository
public class ClientsRepository {

    static Map<String, Client> clientData;

    static
    {
        clientData = new HashMap<>();
        clientData.put("1",new Client("1","Ana"));
        clientData.put("2",new Client("2","Diego"));
        clientData.put("3",new Client("3","Ione"));
        clientData.put("4",new Client("4","Xurxo"));
        clientData.put("5",new Client("5","Marc"));
        clientData.put("6",new Client("6","María"));
        clientData.put("7",new Client("7","Carlos"));
        clientData.put("8",new Client("8","Admin"));
        clientData.put("9",new Client("9","Jose"));
        clientData.put("10",new Client("10","Antía"));

    }


    public Flux<Client> findAllClients()
    {
        return Flux.fromIterable(clientData.values());
    }

}
