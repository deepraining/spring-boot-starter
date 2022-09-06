package dr.sbs.common;

import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class CommonPage<T> {
  private Integer pageNum;
  private Integer pageSize;
  private Integer pages;
  private long total;
  private List<T> list;

  public static <T> CommonPage<T> toPage(List<T> list) {
    CommonPage<T> result = new CommonPage<T>();
    PageInfo<T> pageInfo = new PageInfo<T>(list);

    result.setPages(pageInfo.getPages());
    result.setPageNum(pageInfo.getPageNum());
    result.setPageSize(pageInfo.getPageSize());
    result.setTotal(pageInfo.getTotal());
    result.setList(pageInfo.getList());

    return result;
  }

  public static <T> CommonPage<T> toPage(Page<T> pageInfo) {
    CommonPage<T> result = new CommonPage<T>();

    result.setPages(pageInfo.getTotalPages());
    result.setPageNum(pageInfo.getNumber());
    result.setPageSize(pageInfo.getSize());
    result.setTotal(pageInfo.getTotalElements());
    result.setList(pageInfo.getContent());

    return result;
  }

  public static <T> CommonPage<T> toPage(PageInfo<T> pageInfo) {
    CommonPage<T> result = new CommonPage<T>();

    result.setPages(pageInfo.getPages());
    result.setPageNum(pageInfo.getPageNum());
    result.setPageSize(pageInfo.getPageSize());
    result.setTotal(pageInfo.getTotal());
    result.setList(pageInfo.getList());

    return result;
  }
}
