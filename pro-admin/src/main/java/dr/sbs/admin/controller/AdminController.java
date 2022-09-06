package dr.sbs.admin.controller;

import dr.sbs.admin.dto.AdminLoginParam;
import dr.sbs.admin.dto.AdminUpdatePasswordParam;
import dr.sbs.admin.dto.AdminUserParam;
import dr.sbs.admin.service.AdminUserService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.AdminRole;
import dr.sbs.mbg.model.AdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 后台用户管理 */
@Controller
@Api(tags = "AdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class AdminController {
  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired private AdminUserService userService;

  @ApiOperation(value = "用户注册")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<AdminUser> register(
      @RequestBody @Validated AdminUserParam adminUserParam, BindingResult bindingResult) {
    AdminUser adminUser = userService.register(adminUserParam);
    if (adminUser == null) {
      CommonResult.failed();
    }
    return CommonResult.success(adminUser);
  }

  @ApiOperation(value = "登录以后返回token")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Map<String, String>> login(
      @RequestBody @Validated AdminLoginParam adminLoginParam, BindingResult bindingResult) {
    String token = userService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
    if (token == null) {
      return CommonResult.validateFailed("用户名或密码错误");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation(value = "刷新token")
  @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<Map<String, String>> refreshToken(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader);
    String refreshToken = userService.refreshToken(token);
    if (refreshToken == null) {
      return CommonResult.failed("token已经过期！");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", refreshToken);
    tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation(value = "获取当前登录用户信息")
  @RequestMapping(value = "/info", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<Map<String, Object>> getAdminInfo(Principal principal) {
    if (principal == null) {
      return CommonResult.unauthorized();
    }
    String username = principal.getName();
    AdminUser adminUser = userService.getUserByUsername(username);
    Map<String, Object> data = new HashMap<>();
    data.put("username", adminUser.getUsername());
    data.put("nickname", adminUser.getNickname());
    data.put("avatar", adminUser.getAvatar());
    data.put("roles", new String[] {"NONE"});
    data.put("menus", userService.getMenuList(adminUser.getId()));
    return CommonResult.success(data);
  }

  @ApiOperation(value = "登出功能")
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Object> logout() {
    return CommonResult.success(null);
  }

  @ApiOperation("根据用户名或姓名分页获取用户列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<AdminUser>> list(
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<AdminUser> adminList = userService.list(keyword, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(adminList));
  }

  @ApiOperation("获取指定用户信息")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<AdminUser> getItem(@PathVariable Long id) {
    AdminUser adminUser = userService.getItem(id);
    return CommonResult.success(adminUser);
  }

  @ApiOperation("修改指定用户信息")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated AdminUser adminUser,
      BindingResult bindingResult) {
    int count = userService.update(id, adminUser);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("修改指定用户密码")
  @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> updatePassword(
      @RequestBody @Validated AdminUpdatePasswordParam updatePasswordParam,
      BindingResult bindingResult) {
    int status = userService.updatePassword(updatePasswordParam);
    if (status > 0) {
      return CommonResult.success(status);
    } else if (status == -1) {
      return CommonResult.failed("提交参数不合法");
    } else if (status == -2) {
      return CommonResult.failed("找不到该用户");
    } else if (status == -3) {
      return CommonResult.failed("旧密码错误");
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("删除指定用户信息")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    int count = userService.delete(id);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("修改帐号状态")
  @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> updateStatus(
      @PathVariable Long id, @RequestParam(value = "status") Integer status) {
    AdminUser adminUser = new AdminUser();
    adminUser.setStatus(status);
    int count = userService.update(id, adminUser);
    if (count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("给用户分配角色")
  @RequestMapping(value = "/role/update", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> updateRole(
      @RequestParam("userId") Long userId, @RequestParam("roleIds") List<Long> roleIds) {
    int count = userService.updateRole(userId, roleIds);
    if (count >= 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @ApiOperation("获取指定用户的角色")
  @RequestMapping(value = "/role/{userId}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<AdminRole>> getRoleList(@PathVariable Long userId) {
    List<AdminRole> roleList = userService.getRoleList(userId);
    return CommonResult.success(roleList);
  }
}
