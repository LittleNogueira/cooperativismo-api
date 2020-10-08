package br.com.nogueira.cooperativismo.clients;

import br.com.nogueira.cooperativismo.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "users",url = "${client.user.url}")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{cpf}")
    UserDto buscarUsuarioPorCpf(@PathVariable String cpf);

}
