package dr.sbs.wx.controller;

import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.WxUser;
import dr.sbs.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Account", description = "账号管理")
@RequestMapping("/account")
public class AccountController extends BaseController {
  @Autowired private UserService userService;

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @ApiOperation(value = "小程序登陆")
  @RequestMapping(value = "/miniLogin", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Map<String, String>> miniLogin(
      @RequestParam(value = "code", required = true) @ApiParam(value = "微信授权code", required = true)
          String code) {
    String token = userService.login(code);
    if (token == null) {
      return CommonResult.success(null);
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    tokenMap.put("tokenHeader", tokenHeader);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation(value = "更新用户信息")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @RequestParam(value = "nickname", defaultValue = "")
          @ApiParam(value = "用户昵称", defaultValue = "")
          String nickname,
      @RequestParam(value = "avatar", defaultValue = "") @ApiParam(value = "头像", defaultValue = "")
          String avatar,
      @RequestParam(value = "gender", defaultValue = "") @ApiParam(value = "性别", defaultValue = "")
          Byte gender,
      @RequestParam(value = "province", defaultValue = "") @ApiParam(value = "省", defaultValue = "")
          String province,
      @RequestParam(value = "city", defaultValue = "") @ApiParam(value = "市", defaultValue = "")
          String city) {
    int count = userService.update(nickname, avatar, gender, province, city);
    if (count < 0) {
      return CommonResult.failed("更新失败");
    }
    return CommonResult.success(count);
  }

  @ApiOperation(value = "刷新 token")
  @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<Map<String, String>> refreshToken(HttpServletRequest request) {
    if (!getUserIsLoggedIn()) return CommonResult.unauthorized(null);

    String token = request.getHeader(tokenHeader);
    String refreshToken = userService.refreshToken(token);
    if (refreshToken == null) {
      return CommonResult.failed();
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", refreshToken);
    tokenMap.put("tokenHead", tokenHead);
    tokenMap.put("tokenHeader", tokenHeader);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation(value = "公众号登陆（snsapi_base）")
  @RequestMapping(value = "/mpBaseLogin", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Map<String, String>> mpBaseLogin(
      @RequestParam(value = "code", required = true) @ApiParam(value = "微信授权code", required = true)
          String code) {
    String token = userService.mpBaseLogin(code);
    if (token == null) {
      return CommonResult.failed("Code is not correct");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    tokenMap.put("tokenHeader", tokenHeader);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation(value = "公众号登陆（snsapi_userinfo）")
  @RequestMapping(value = "/mpInfoLogin", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Map<String, String>> mpInfoLogin(
      @RequestParam(value = "code", required = true) @ApiParam(value = "微信授权code", required = true)
          String code) {
    String token = userService.mpInfoLogin(code);
    if (token == null) {
      return CommonResult.failed("Code is not correct");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    tokenMap.put("tokenHeader", tokenHeader);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation("获取用户信息")
  @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<WxUser> userInfo() {
    return CommonResult.success(userService.getCurrentUser());
  }
}
