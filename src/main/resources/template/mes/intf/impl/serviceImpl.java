package amy.mes.mat.intf.impl;

import amy.common.basic.dto.PageCst;
import amy.common.basic.dto.ReqResultDTO;
import amy.common.basic.ex.ExFactory;
import amy.common.utils.DateUtil;
import amy.common.utils.JsonUtils;
import amy.common.utils.StringUtil;
import amy.common.utils.id.IDFactory;
import amy.common.utils.log.ILogger;
import amy.common.utils.log.LogFactory;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class MaterialServiceImpl implements amy.mes.mat.intf.IMaterialService {

    private ILogger logger = LogFactory.getLog(amy.mes.mat.intf.impl.MaterialServiceImpl.class);
    @Autowired
    private amy.mes.mat.dao.MaterialDao materialDao;

    public String queryList(amy.mes.mat.intf.dto.QueryMaterialDTO dto) {
        String retJsonParam = null;
        ReqResultDTO rdto = new ReqResultDTO();

        try {
            if (null == dto) {
                throw ExFactory.getInstance().getBusinessEx("NULL", "MES-000001");
            }
            PageCst<amy.mes.mat.vo.MaterialVo> datas = PageCst.setPage(dto);
            String whereSql = queryListCoundtion(dto);

            List<amy.mes.mat.vo.MaterialVo> dtoList = null;
            if (dto.getIsCount()) {
                PageHelper.startPage(datas.getPageNo().intValue(), datas.getPageSize().intValue(),
                        datas.getIsCount().booleanValue());
                Page page = (Page) this.materialDao.selectByCondition(whereSql);
                dtoList = page.getResult();
                datas.setTotal(Long.valueOf(page.getTotal()));
                datas.setPages(Integer.valueOf(page.getPages()));
            } else {
                dtoList = this.materialDao.selectByCondition(whereSql);
                datas.setTotal(Long.valueOf(dtoList.size()));
            }
            datas.setResult(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            rdto.setExecResult(false);
            rdto.setExecMsg("获取数据列表异常！");
            rdto.setExecErrCode(ExFactory.getExSeat());
        }

        retJsonParam = JsonUtils.toJsonStr(rdto);
        return retJsonParam;
    }

    private String queryListCoundtion(amy.mes.mat.intf.dto.QueryMaterialDTO dto) {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" IFNULL(dr,0) = 0 ");

        if (StringUtil.isNotBlank(dto.getSearchKey())) {
            sbSql.append("and name like '")
                    .append("%"+dto.getSearchKey() + "%")
                    .append("' ");
        }

        sbSql.append(" order by dtcreate  desc ");


        return sbSql.toString();
    }

    public String save(amy.mes.mat.vo.MaterialVo vo) {
        String retJsonParam = null;
        ReqResultDTO rdto = new ReqResultDTO();

        try {
            if (null == vo) {
                throw ExFactory.getInstance().getBusinessEx("NULL", "CRM-000001");
            }

            Date currentDate = DateUtil.getCurrentDate();
            if (StringUtil.isBlank(vo.getPk())) {
                vo.setPk(IDFactory.getInstance().getID());

                vo.setDtcreate(currentDate);
                vo.setPk_updator(vo.getPk_creator());
                vo.setDtupdate(currentDate);
                vo.setDr(0);
                this.materialDao.insert(vo);
            } else {
                vo.setPk_updator(vo.getPk_creator());
                vo.setDtupdate(currentDate);
                this.materialDao.updateByPrimaryKey(vo);
            }


            rdto.setExecResult(true);
            rdto.setExecMsg("");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            rdto.setExecResult(false);
            rdto.setExecMsg("保存异常！");
            rdto.setExecErrCode(ExFactory.getExSeat());
        }

        retJsonParam = JsonUtils.toJsonStr(rdto);
        return retJsonParam;
    }

    public String deleteByPk(amy.mes.mat.vo.MaterialVo vo) {
        String retJsonParam = null;
        ReqResultDTO rdto = new ReqResultDTO();

        try {
            if (null == vo) {
                throw ExFactory.getInstance().getBusinessEx("NULL", "CRM-000001");
            }

            if (StringUtil.isBlank(vo.getPk())) {
                return rtnReqResultStr(rdto, "主键不能为空");
            }

            if (StringUtil.isBlank(vo.getPk_updator())) {
                return rtnReqResultStr(rdto, "操作人不能为空");
            }

            int count = this.materialDao.discardByPrimaryKey(vo);
            if (count > 0) {
                rdto.setExecResult(true);
                rdto.setExecMsg("");
            } else {
                rdto.setExecResult(false);
                rdto.setExecMsg("删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            rdto.setExecResult(false);
            rdto.setExecMsg("删除异常！");
            rdto.setExecErrCode(ExFactory.getExSeat());
        }

        retJsonParam = JsonUtils.toJsonStr(rdto);
        return retJsonParam;
    }

    public String queryByPk(String pk) {
        String retJsonParam = null;
        ReqResultDTO rdto = new ReqResultDTO();

        try {
            if (StringUtil.isBlank(pk)) {
                throw ExFactory.getInstance().getBusinessEx("NULL", "CRM-000001");
            }

            amy.mes.mat.vo.MaterialVo vo = this.materialDao.selectByPrimaryKey(pk);
            if (null != vo) {
                rdto.setExecDatas(vo);
                rdto.setExecResult(true);
                rdto.setExecMsg("");
            } else {
                rdto.setExecResult(false);
                rdto.setExecMsg("无数据");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            rdto.setExecResult(false);
            rdto.setExecMsg("获取数据异常！");
            rdto.setExecErrCode(ExFactory.getExSeat());
        }

        retJsonParam = JsonUtils.toJsonStr(rdto);
        return retJsonParam;
    }


    private String rtnReqResultStr(ReqResultDTO rdto, String msg) {
        rdto.setExecResult(false);
        rdto.setExecMsg(msg);
        return JsonUtils.toJsonStr(rdto);
    }


}
