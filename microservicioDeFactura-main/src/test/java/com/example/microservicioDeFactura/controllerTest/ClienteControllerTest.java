package com.example.microservicioDeFactura.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.microservicioDeFactura.controller.ClienteController;
import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    /**
     * Inicializa un cliente de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombreEmpresa("Empresa Prueba");
        cliente.setRutEmpresa("12345678-9");
        cliente.setContacto("Juan Pérez");
        cliente.setCorreo("contacto@empresa.cl");
        cliente.setDireccion("Calle Falsa 123");
    }

    /**
     * Prueba el endpoint para listar todos los clientes.
     */
    @Test
    void testListarTodos() throws Exception {
        when(clienteService.listarTodos()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/listarTodos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rutEmpresa").value(cliente.getRutEmpresa()));
    }

    /**
     * Prueba el endpoint para obtener un cliente por ID (encontrado).
     */
    @Test
    void testObtenerPorId_found() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/obtenerPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutEmpresa").value(cliente.getRutEmpresa()));
    }

    /**
     * Prueba el endpoint para obtener un cliente por ID (no encontrado).
     */
    @Test
    void testObtenerPorId_notFound() throws Exception {
        when(clienteService.buscarPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/clientes/obtenerPorId/2"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba el endpoint para crear un nuevo cliente.
     */
    @Test
    void testCrearCliente() throws Exception {
        when(clienteService.guardar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/v1/clientes/crearCliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutEmpresa").value(cliente.getRutEmpresa()));
    }

    /**
     * Prueba el endpoint para actualizar un cliente existente (encontrado).
     */
    @Test
    void testActualizarCliente_found() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(clienteService.guardar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/v1/clientes/actualizarClientePorId/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutEmpresa").value(cliente.getRutEmpresa()));
    }

    /**
     * Prueba el endpoint para actualizar un cliente (no encontrado).
     */
    @Test
    void testActualizarCliente_notFound() throws Exception {
        when(clienteService.buscarPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/clientes/actualizarClientePorId/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba el endpoint para eliminar un cliente por ID (encontrado).
     */
    @Test
    void testEliminarCliente_found() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/clientes/eliminarClientePorId/1"))
                .andExpect(status().isNoContent());
    }

    /**
     * Prueba el endpoint para eliminar un cliente por ID (no encontrado).
     */
    @Test
    void testEliminarCliente_notFound() throws Exception {
        when(clienteService.buscarPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/clientes/eliminarClientePorId/2"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba el endpoint para buscar cliente por RUT (encontrado).
     */
    @Test
    void testObtenerPorRut_found() throws Exception {
        when(clienteService.buscarPorRut("12345678-9")).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/obtenerPorRut/12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreEmpresa").value(cliente.getNombreEmpresa()));
    }

    /**
     * Prueba el endpoint para buscar cliente por RUT (no encontrado).
     */
    @Test
    void testObtenerPorRut_notFound() throws Exception {
        when(clienteService.buscarPorRut("00000000-0")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/clientes/obtenerPorRut/00000000-0"))
                .andExpect(status().isNotFound());
    }

}