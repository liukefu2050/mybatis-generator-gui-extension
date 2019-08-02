package amy.mes.mat.intf;


import amy.mes.mat.intf.dto.QueryMaterialDTO;
import amy.mes.mat.vo.MaterialVo;

public interface IMaterialService {

  String queryList(QueryMaterialDTO dto);

  String deleteByPk(MaterialVo vo);

  String queryByPk(String pk);

  String save(MaterialVo vo);
}
