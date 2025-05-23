package codesquad.team01.issuetracker.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.user.dto.UserDto;
import codesquad.team01.issuetracker.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserQueryRepository userQueryRepository;

	public UserDto.UserFilterListResponse findUsersForFilter() {

		List<UserDto.WriterResponse> users = userQueryRepository.findUsersForFilter();

		return UserDto.UserFilterListResponse.builder()
			.totalCount(users.size())
			.users(users)
			.build();
	}
}
