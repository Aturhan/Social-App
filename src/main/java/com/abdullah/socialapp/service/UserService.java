package com.abdullah.socialapp.service;

import com.abdullah.socialapp.dto.AuthRequest;
import com.abdullah.socialapp.dto.CreateUserRequest;
import com.abdullah.socialapp.dto.FollowUserRequest;
import com.abdullah.socialapp.dto.SearchUserResponse;
import com.abdullah.socialapp.exception.UserAlreadyFollowedException;
import com.abdullah.socialapp.exception.UserNotFoundException;
import com.abdullah.socialapp.model.Role;
import com.abdullah.socialapp.model.UserModel;
import com.abdullah.socialapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findUserByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public SearchUserResponse getUserByUsername(String username) {
            UserModel user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found!"));
            return convertToSearchResponse(user);
    }

    public List<SearchUserResponse> getAllUsers(){
        List<UserModel> userModelList = userRepository.findAll();
        return userModelList.stream()
                .map(this::convertToSearchResponse).collect(Collectors.toList());
    }


    @Transactional
    public void addUser(CreateUserRequest request){
        final UserModel userModel = converter(request);
        userRepository.save(userModel);
    }



    public void followUser(FollowUserRequest request) throws UserAlreadyFollowedException {
       UserModel toBeFollowed = userRepository.findUserByUsername(request.username())
               .orElseThrow(() -> new UserNotFoundException("User not found!"));
       UserModel following = userRepository.findUserByUsername(request.yourUsername())
               .orElseThrow(() -> new UserNotFoundException("User not found!"));

       if (toBeFollowed.getFollowers().contains(request.yourUsername())){
           throw new UserAlreadyFollowedException("User already following");
       }
       toBeFollowed.addFollower(request.yourUsername());
       userRepository.save(toBeFollowed);
       following.addFollowing(request.username());
       userRepository.save(following);

    }

    @Transactional
    public void  removeUser(String username){
       final UserModel userModel = userRepository.findUserByUsername(username)
               .orElseThrow(() -> new UserNotFoundException("User not found!"));
       userRepository.delete(userModel);
    }

    protected UserModel findUserForPost(String username){
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }


    private UserModel converter(CreateUserRequest request){

       return UserModel.builder()
                .fullName(request.fullName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
               .authorities(Set.of(Role.USER))
                .followers(Set.of("Social"))
                .followings(Set.of("Social"))
               .accountNonExpired(true)
               .accountNonLocked(true)
               .credentialsNonExpired(true)
               .isEnabled(true)
                .build();
    }

    private SearchUserResponse convertToSearchResponse(UserModel user){
            return SearchUserResponse.builder()
                    .fullName(user.getFullName())
                    .username(user.getUsername())
                    .posts((user.getPosts() != null) ? user.getPosts().size() : 0 )
                    .followingsCount((user.getFollowings() != null) ? user.getFollowings().size() : 0)
                    .followersCount((user.getFollowers() != null) ? user.getFollowers().size() : 0)
                    .build();
        }


}
