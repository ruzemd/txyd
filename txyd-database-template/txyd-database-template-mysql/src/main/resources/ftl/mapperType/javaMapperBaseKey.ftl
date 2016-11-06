<#-- 类文件的package模版 -->
package ${jcb.basePackageBaseMapper?lower_case};

<#-- 类文件的import模版 -->
import java.util.List;
import java.util.LinkedHashMap;
import org.apache.ibatis.annotations.Param;
/**
 * 
 * @author ${jcb.author}
 * @param <T>
 * @param <ID>
 */
public interface ${BaseKeyMapper}<T,ID> {
	T getById(ID id);
	List<T> getByIds(List<ID> ids);

	Integer updateById(@Param("${jcb.tableAlias}") T updateAlias, @Param("id") ID id);
    Integer updateByIds(@Param("${jcb.tableAlias}") T updateAlias, @Param("ids")List<ID> ids);

	Integer deleteById(ID id);
	Integer deleteByIds(List<ID> list);
}