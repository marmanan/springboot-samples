package es.santander.darwin.reactivecontroller.web;

import es.santander.darwin.reactivecontroller.model.Client;
import es.santander.darwin.reactivecontroller.repository.ClientsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/client")
@Tag(name = "Clients", description = "the Clients API")
public class RestControllerReactive {


	private ClientsRepository clientsRepository;

	public RestControllerReactive(ClientsRepository clientsRepository) {
		this.clientsRepository = clientsRepository;
	}

	@Operation(summary = "Find all New Clients", description = "Find all New Clients of the day", security = {@SecurityRequirement(name= "BearerAuth")})
	@GetMapping("/newClients")
	public Flux<Client> getNewClients() {
		return clientsRepository.findAllClients();
	}

	@Operation(summary = "Find all Clients", description = "Find all Clients from LDAP")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))) })
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public Mono<String> getAllClients() {
		return Mono.just("Get all clients");
	}

	@RequestMapping(value="/all", method = RequestMethod.POST)
	public Mono<String> postAllClients() {
		return Mono.just("Post all clients");
	}

	@RequestMapping(value="/all", method = RequestMethod.GET, headers = {"Session-Id=sessionId"})
	public Mono<String> getAllClientsWithHeaders(@RequestHeader("Session-Id") String sessionId){
		return Mono.just("Get all client with Headers: " + sessionId);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public Mono<String> getClientByName(@Parameter(description="name of the client to be update. Cannot be empty.",required=true)@PathVariable("name") String name){
		return Mono.just("Get client with name=" + name);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Mono<String> getClientById(@RequestParam("id") String id){
		return Mono.just("Get client with id=" + id);
	}

	@Operation(summary = "Update an existing client", description = "", tags = { "client" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
			@ApiResponse(responseCode = "404", description = "Contact not found"),
			@ApiResponse(responseCode = "405", description = "Validation exception") })
	@PutMapping("/{id}/{name}")
	public Mono<String> newClient(@PathVariable String id, @PathVariable String name){
		return Mono.just("New client with id=" + id + " and name=" + name);
	}

	@DeleteMapping("/{id}")
	public Mono<String> deleteClient(@PathVariable String id){
		return Mono.just("Delete client with id=" + id);
	}
}
