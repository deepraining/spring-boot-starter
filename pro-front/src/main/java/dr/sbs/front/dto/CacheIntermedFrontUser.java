package dr.sbs.front.dto;

import dr.sbs.mbg.model.FrontUser;
import lombok.Getter;
import lombok.Setter;

/** 因为redis存储时会序列化为JSON，有些字段会丢失，所以在这里保留 */
@Getter
@Setter
public class CacheIntermedFrontUser extends FrontUser {
  private String internalPassword;
}
