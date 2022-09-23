package dr.sbs.proto.controller;

import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.proto.all.AllProto;
import dr.sbs.proto.dto.UpdatePasswordParam;
import dr.sbs.proto.dto.UserCreateParam;
import dr.sbs.proto.service.UserService;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/account")
public class AccountController {
  @Autowired private UserService userService;

  public CommonResult<FrontUser> register(UserCreateParam userCreateParam) {
    FrontUser user = userService.register(userCreateParam);
    if (user != null) return CommonResult.success(user);
    return CommonResult.failed();
  }

  @PostMapping(
      value = "/register",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> registerPB(InputStream reqStream) throws IOException {
    AllProto.UserCreateParamProto userCreateParamProto =
        AllProto.UserCreateParamProto.parseFrom(reqStream);
    UserCreateParam userCreateParam = new UserCreateParam();
    userCreateParam.setUsername(userCreateParamProto.getUsername());
    userCreateParam.setEmail(userCreateParamProto.getEmail());
    userCreateParam.setPassword(userCreateParamProto.getPassword());

    CommonResult<FrontUser> result = register(userCreateParam);

    return ProtoUtil.responseFrontUser(result);
  }

  public CommonResult<Integer> updatePassword(UpdatePasswordParam updatePasswordParam) {
    int count = userService.updatePassword(updatePasswordParam);
    if (count > 0) return CommonResult.success(count);
    return CommonResult.failed();
  }

  @PostMapping(
      value = "/updatePassword",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> updatePasswordPB(InputStream reqStream) throws IOException {
    AllProto.UpdatePasswordParamProto updatePasswordParamProto =
        AllProto.UpdatePasswordParamProto.parseFrom(reqStream);
    UpdatePasswordParam updatePasswordParam = new UpdatePasswordParam();
    updatePasswordParam.setOldPassword(updatePasswordParamProto.getOldPassword());
    updatePasswordParam.setNewPassword(updatePasswordParamProto.getNewPassword());

    CommonResult<Integer> result = updatePassword(updatePasswordParam);

    return ProtoUtil.responseInteger(result);
  }

  public CommonResult<FrontUser> currentUser() {
    FrontUser user = userService.getCurrentUser();
    if (user != null) return CommonResult.success(user);
    return CommonResult.failed();
  }

  @PostMapping(
      value = "/currentUser",
      headers = {"content-type=application/octet-stream"})
  @ResponseBody
  public ResponseEntity<byte[]> currentUserPB() {
    CommonResult<FrontUser> result = currentUser();

    return ProtoUtil.responseFrontUser(result);
  }
}
