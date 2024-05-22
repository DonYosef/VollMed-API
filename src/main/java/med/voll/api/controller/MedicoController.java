package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public void cargarDatosMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        medicoRepository.save(new Medico(datosRegistroMedico));
    }

    @GetMapping
    public Page<DatosListadoMedico> listadoDeMedicos(@PageableDefault(size = 3) Pageable paginacion){
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
        /*return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);*/
    }

    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatoActualizarMedico actualizarMedico){
        Medico medico = medicoRepository.getReferenceById(actualizarMedico.id());
        medico.actualizarDatos(actualizarMedico);
    }

    //Delete lógico (no se borra en la base de dato, solo desactiva el elemento)
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
    }


/*    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/
}
