package dr.sbs.wx.controller;

import dr.sbs.mbg.model.WxUser;
import dr.sbs.wx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
  @Autowired private UserService userService;

  // 获取用户是否已登录
  protected Boolean getUserIsLoggedIn() {
    WxUser wxUser = userService.getCurrentUser();
    return wxUser != null;
  }
}
