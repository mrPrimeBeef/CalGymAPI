package app.dtos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record OptionDTO(Date date, LocalDateTime time, List<VoteDTO> voteList) {
}
