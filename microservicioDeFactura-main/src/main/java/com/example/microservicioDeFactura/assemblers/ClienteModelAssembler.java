package com.example.microservicioDeFactura.assemblers;

import com.example.microservicioDeFactura.controller.ClienteController;
import com.example.microservicioDeFactura.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    @org.springframework.lang.NonNull
    public EntityModel<Cliente> toModel(@org.springframework.lang.NonNull Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).obtenerPorRut(cliente.getRutEmpresa())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listarTodos()).withRel("clientes")
        );
    }

}
