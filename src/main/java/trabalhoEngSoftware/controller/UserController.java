package trabalhoEngSoftware.controller;


import trabalhoEngSoftware.controller.request.CreateUserRequest;
import trabalhoEngSoftware.controller.request.UserUpdateRequest;
import trabalhoEngSoftware.controller.response.IdResponse;
import trabalhoEngSoftware.controller.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import trabalhoEngSoftware.service.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private GetUserInfoService getUserInfoService;

    @Autowired
    private UpdateUserService updateUserService;

    @Autowired
    private DeleteUserService deleteUserService;

    @Autowired
    private GetUserByIdService getUserByIdService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse create(@Valid @RequestBody CreateUserRequest request){
        return createUserService.create(request);
    }

    @GetMapping ("/search-name")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse search(@RequestParam(name = "text") String search) {
        return getUserInfoService.search(search);
    }

    @GetMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse get(@PathVariable Long id) {
        return getUserByIdService.get(id);
    }

    @PutMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse modify(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return updateUserService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deleteUserService.delete(id);
    }

}
