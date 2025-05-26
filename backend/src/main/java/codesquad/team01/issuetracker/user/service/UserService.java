package codesquad.team01.issuetracker.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.user.dto.UserDto;
import codesquad.team01.issuetracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserDto.UserFilterListResponse findUsersForFilter() {

		List<UserDto.WriterResponse> users = userRepository.findUsersForFilter();

		return UserDto.UserFilterListResponse.builder()
			.totalCount(users.size())
			.users(users)
			.build();
	}
}
