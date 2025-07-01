package com.example.microservicioDeFactura.serviceTest;

import com.example.microservicioDeFactura.service.ResiduoService;
import com.example.microservicioDeFactura.controller.ResiduoController;
import com.example.microservicioDeFactura.model.Residuo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResiduoServiceTest {

    @Mock
    private ResiduoService residuoService;

    @InjectMocks
    private ResiduoController residuoController;

    private Residuo residuo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        residuo = new Residuo();
        residuo.setId(1);
        residuo.setNombre("Residuo Test");
        residuo.setTipo("Orgánico");
        residuo.setPeso(50.0);
        residuo.setUnidadMedida("kg");
        residuo.setPeligrosidad("Baja");
        residuo.setEmpresaEmisora("Empresa Residuo");
        residuo.setVolumen(10.0);
        residuo.setClasificacion("Reciclable");
    }

    @Test
    void testListarResiduos() {
        when(residuoService.findAll()).thenReturn(List.of(residuo));

        var response = residuoController.listar();

        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection());
        assertNotNull(response.getBody(), "Response body should not be null before checking size");
        if (response.getBody() != null) {
            assertNotNull(response.getBody(), "Response body should not be null before checking size");
            if (response.getBody() != null) {
                assertNotNull(response.getBody(), "Response body should not be null before checking size");
                List<Residuo> body = response.getBody();
                assertNotNull(body, "Body should not be null before checking size");
                assertEquals(1, body.size());
                assertEquals(residuo.getId(), body.get(0).getId());
            }
        }
    }

    @Test
    void testListarResiduosVacio() {
        when(residuoService.findAll()).thenReturn(List.of());

        var response = residuoController.listar();

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testCrearResiduo() {
        doNothing().when(residuoService).guardar(any(Residuo.class));

        var response = residuoController.guardar(residuo);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testCrearResiduoNull() {
        var response = residuoController.guardar(null);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testBuscarPorId_found() {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));

        var response = residuoController.buscarPorId(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody(), "Response body should not be null before accessing getId()");
        if (response.getBody() != null) {
            assertNotNull(response.getBody(), "Response body should not be null before accessing getId()");
            assertNotNull(response.getBody(), "Response body should not be null before accessing getId()");
            if (response.getBody() != null) {
                assertNotNull(response.getBody(), "Response body should not be null before accessing getId()");
                if (response.getBody() != null) {
                    assertNotNull(response.getBody(), "Response body should not be null before accessing getId()");
                    if (response.getBody() != null) {
                        assertNotNull(response.getBody(), "Response body should not be null before accessing getId()");
                        Residuo responseBody = response.getBody();
                        assertNotNull(responseBody, "Response body should not be null before accessing getId()");
                        assertEquals(residuo.getId(), responseBody.getId());
                    }
                }
            }
        }
    }

    @Test
    void testBuscarPorId_notFound() {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        var response = residuoController.buscarPorId(2);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testEliminarPorId_found() {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));
        doNothing().when(residuoService).eliminarPorId(1);

        var response = residuoController.eliminarPorId(1);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testEliminarPorId_notFound() {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        var response = residuoController.eliminarPorId(2);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testActualizar_found() {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));
        doNothing().when(residuoService).actualizar(eq(1), any(Residuo.class));

        var response = residuoController.actualizar(1, residuo);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testActualizar_notFound() {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        var response = residuoController.actualizar(2, residuo);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testHealth() {
        String result = residuoController.health();
        assertEquals("Service is running!", result);
    }
}
