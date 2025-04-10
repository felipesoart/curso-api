package joaopaulofelipe.api.services.impl;

import joaopaulofelipe.api.domain.User;
import joaopaulofelipe.api.domain.dto.UserDTO;
import joaopaulofelipe.api.repositories.UserRepository;
import joaopaulofelipe.api.services.UserService;
import joaopaulofelipe.api.services.exceptions.DataIntegratyViolationException;
import joaopaulofelipe.api.services.exceptions.ObjctNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjctNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public User update(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UserDTO obj){
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if (user.isPresent() && !user.get().getId().equals(obj.getId())){
            throw  new DataIntegratyViolationException("E-mail já cadastrado no sistema");
        }
    }
}
