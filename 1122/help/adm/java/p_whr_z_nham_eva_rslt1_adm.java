
package com.duzon.ctd.web.components.erpiu.customize.nham;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duzon.ctd.components.annotation.DefaultViewPath;
import com.duzon.ctd.components.exception.DzApplicationRuntimeException;
import com.duzon.ctd.components.model.DzRequestModel;
import com.duzon.ctd.components.model.DzResponseModel;
import com.duzon.ctd.data.DataTable;
import com.duzon.ctd.jdbc.objects.SqlPack;
import com.duzon.ctd.jdbc.transaction.DbTransaction;
import com.duzon.ctd.util.JsonUtil;
import com.duzon.ctd.util.StringUtil;
import com.duzon.ctd.web.components.erpiu.DzErpiuServiceBase;
/*
 * 작성자 : 
 * 작성일 : 2018-04-16
 * 메뉴명 : 인사평가 -> 본인평가 관리자
 * 수정자 : 
 * 수정일 :
 * 수정내용 : 
 */
@DefaultViewPath(path="/customize/nham/")
@Service("p_whr_z_nham_eva_rslt1_adm")
public class p_whr_z_nham_eva_rslt1_adm extends DzErpiuServiceBase {
	
	@Override
	protected String getServiceName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}	
	@Override
	protected DzResponseModel executeInternal(DzRequestModel requestModel) throws Exception {
		
		this.logger.info("ORDER executeInternal start ::: p_whr_z_nham_eva_rslt1_adm");
		
		// TODO Auto-generated method stub
		DzResponseModel responseModel = null;
		try{		
			responseModel = new DzResponseModel();	
			String companyCode = this.getCompanyCode();	//회사코드
			if(StringUtil.equals(requestModel.getActionName(), "planCheck")){ 
				DataTable dt = this.PlanCheck(requestModel, companyCode);
				this.displayDataTable(dt);				
				responseModel.setResultDataTable(dt);
			}else if(StringUtil.equals(requestModel.getActionName(), "getList")){ 
				DataTable dt = this.GetList(requestModel, companyCode);
				this.displayDataTable(dt);				
				responseModel.setResultDataTable(dt);
			}else if(StringUtil.equals(requestModel.getActionName(), "rsltSclList")){ 
				DataTable dt = this.GetRsltSclList(requestModel, companyCode);
				this.displayDataTable(dt);				
				responseModel.setResultDataTable(dt);
			}else if(StringUtil.equals(requestModel.getActionName(), "rsltScmList")){ 
				DataTable dt = this.GetRsltScmList(requestModel, companyCode);
				this.displayDataTable(dt);				
				responseModel.setResultDataTable(dt);
			}else if(StringUtil.equals(requestModel.getActionName(), "rslt1Insert")){ 
				this.Save(requestModel, companyCode);
//				responseModel.setResultDataTable(dt);
			}else if(StringUtil.equals(requestModel.getActionName(), "checkData")){ 
				int changeCnt = this.CheckData(requestModel, companyCode);
				responseModel.setResultInteger("CHANGE_CNT", changeCnt);
			}else if(StringUtil.equals(requestModel.getActionName(), "checkDeadline")){ 
				int deadlineCnt = this.CheckDeadline(requestModel, companyCode);
				responseModel.setResultInteger("DEADLINE_CNT", deadlineCnt);
			}else if(StringUtil.equals(requestModel.getActionName(), "deadLineUpdate")){ 
				this.DeadLineUpdate(requestModel, companyCode);
//				responseModel.setResultDataTable(dt);
			}
		}catch(DzApplicationRuntimeException e){
			responseModel.setSuccess(false);
			throw e;
		}catch(Exception e){
			responseModel.setSuccess(false);
			throw e;
		}
		
		responseModel.setSuccess(true);
		return responseModel;
	}
	
	//평가계획조회
	private DataTable PlanCheck(DzRequestModel dzRequestModel, String companyCode){
		DataTable dt = null;
		
		try{
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_evunit = requestModel.getParameterValue("cd_evunit");
			String tp_evlu = requestModel.getParameterValue("tp_evlu");
			
			SqlPack	sp = new SqlPack();
			String sqlText = "UP_WHR_Z_NHAM_EVA_PLAN_S";
			sp.setStoreProcedure(true);
			sp.setSqlText(sqlText);
			sp.getInParameters().put("P_CD_COMPANY",  companyCode);
			sp.getInParameters().put("P_YM_EVLU",   ym_evlu);
			sp.getInParameters().put("P_CD_EVUNIT",   cd_evunit);
			sp.getInParameters().put("P_CD_EVLUTYPE",   tp_evlu);
			sp.getInParameters().put("P_MENU_ID",  "RSLT12");
			
	        dt = this.getDataManager().queryForDataTable(sp);
			
		}catch(Exception e){
			throw new DzApplicationRuntimeException(e);
		}
		return dt;	
	}
	
	private DataTable GetList(DzRequestModel dzRequestModel, String companyCode){
		DataTable dt = null;
		
		try{
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_evunit = requestModel.getParameterValue("cd_evunit");
			String tp_evlu = requestModel.getParameterValue("tp_evlu");
			String cd_evgroup = requestModel.getParameterValue("cd_evgroup");
			String no_emp = requestModel.getParameterValue("no_emp"); //조회조건에서 선택된 사원번호
			
			SqlPack	sp = new SqlPack();
			String sqlText = "UP_WHR_Z_NHAM_EVA_RSLT_LS";
			sp.setStoreProcedure(true);
			sp.setSqlText(sqlText);
			sp.getInParameters().put("P_CD_COMPANY",  companyCode);
			sp.getInParameters().put("P_YM_EVLU",   ym_evlu);
			sp.getInParameters().put("P_CD_EVUNIT",   cd_evunit);
			sp.getInParameters().put("P_TP_EVLU",   tp_evlu);
			sp.getInParameters().put("P_CD_EVGROUP",  cd_evgroup);
			sp.getInParameters().put("P_S_CD_EVGROUP",  cd_evgroup);
			sp.getInParameters().put("P_NO_EMP",   no_emp);
			sp.getInParameters().put("P_S_NO_EMP",   no_emp);
			
	        dt = this.getDataManager().queryForDataTable(sp);
			
		}catch(Exception e){
			throw new DzApplicationRuntimeException(e);
		}
		return dt;	
	}
	
	private DataTable GetRsltSclList(DzRequestModel dzRequestModel, String companyCode){
		DataTable dt = null;
		
		try{
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_scaleh = requestModel.getParameterValue("cd_scaleh");
			
			SqlPack	sp = new SqlPack();
			String sqlText = "UP_WHR_Z_NHAM_EVA_RSLT_SCL_S";
			sp.setStoreProcedure(true);
			sp.setSqlText(sqlText);
			sp.getInParameters().put("P_CD_COMPANY",  companyCode);
			sp.getInParameters().put("P_YM_EVLU",   ym_evlu);
			sp.getInParameters().put("P_CD_SCALEH",   cd_scaleh);
			
	        dt = this.getDataManager().queryForDataTable(sp);
			
		}catch(Exception e){
			throw new DzApplicationRuntimeException(e);
		}
		return dt;	
	}
	
	private DataTable GetRsltScmList(DzRequestModel dzRequestModel, String companyCode){
		DataTable dt = null;
		
		try{
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_scaleh = requestModel.getParameterValue("cd_scaleh");
			String cd_scaled = requestModel.getParameterValue("cd_scaled");
			
			SqlPack	sp = new SqlPack();
			String sqlText = "UP_WHR_Z_NHAM_EVA_RSLT_SCM_S";
			sp.setStoreProcedure(true);
			sp.setSqlText(sqlText);
			sp.getInParameters().put("P_CD_COMPANY",  companyCode);
			sp.getInParameters().put("P_YM_EVLU",   ym_evlu);
			sp.getInParameters().put("P_CD_SCALEH",   cd_scaleh);
			sp.getInParameters().put("P_CD_SCALED",   cd_scaled);

	        dt = this.getDataManager().queryForDataTable(sp);
			
		}catch(Exception e){
			throw new DzApplicationRuntimeException(e);
		}
		return dt;	
	}
	
	@Transactional
	private boolean Save(DzRequestModel requestModel, String companyCode) throws Exception {
		DbTransaction trans = null;
		
		try {
			trans = this.getDataManager().beginTransaction();
			String id_user = this.getUserId();
			
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_evunit = requestModel.getParameterValue("cd_evunit");
			String tp_evlu = requestModel.getParameterValue("tp_evlu");
			String cd_evgroup = requestModel.getParameterValue("cd_evgroup");
			String no_emp = requestModel.getParameterValue("no_emp"); //조회조건에서 선택된 사원번호		
			
			List<Map<String, Object>> gridData = JsonUtil.deserialize(requestModel.getParameterValue("gridData"), ArrayList.class);
			
			for(Map<String, Object> data : gridData) {
				if((int)1 < (int)(Double.parseDouble(data.get("ROW_NO").toString()))) {
					SqlPack so = new SqlPack();
					String procedure = "UP_WHR_Z_NHAM_EVA_RSLT_LS1_U";
					so.setStoreProcedure(true);
					so.setSqlText(procedure);	
					String no_itemseq = data.get("NO_ITEMSEQ").toString();
					so.getInParameters().put("P_CD_COMPANY",  companyCode);
					so.getInParameters().put("P_YM_EVLU",   ym_evlu);
					so.getInParameters().put("P_CD_EVUNIT",   cd_evunit);
					so.getInParameters().put("P_TP_EVLU",   tp_evlu);
					so.getInParameters().put("P_CD_EVGROUP",   cd_evgroup);
					so.getInParameters().put("P_NO_EMP",  no_emp);
					so.getInParameters().put("P_NO_ITEMSEQ",   no_itemseq.substring(0, no_itemseq.length()-2));
					so.getInParameters().put("P_CD_SCALEH",   data.get("CD_SCALEH"));
					so.getInParameters().put("P_CD_RSLT",   data.get("CD_RSLT"));
					so.getInParameters().put("P_NUM_RSLT",   Double.parseDouble(data.get("NUM_RSLT").toString()));
					so.getInParameters().put("P_NUM_CVTRSLT", Double.parseDouble(data.get("NUM_CVTRSLT").toString()));
					so.getInParameters().put("P_DC_RSLT",  data.get("DC_RSLT"));
					so.getInParameters().put("P_ID_UPDATE",   id_user);
				        
					this.update(so);
				}
			}
			trans.commit();
		
		}catch(Exception e){
			if(trans != null){
				trans.rollback();
			}
			throw new DzApplicationRuntimeException(e);
		}
		return true;
	}
	
	private int CheckData(DzRequestModel dzRequestModel, String companyCode){
		DataTable dt = null;
		int changeCnt = 0;
		
		try{
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_evunit = requestModel.getParameterValue("cd_evunit");
			String tp_evlu = requestModel.getParameterValue("tp_evlu");
			String cd_evgroup = requestModel.getParameterValue("cd_evgroup");
			String no_emp = requestModel.getParameterValue("no_emp"); //조회조건에서 선택된 사원번호
			
			List<Map<String, Object>> gridData = JsonUtil.deserialize(requestModel.getParameterValue("chData"), ArrayList.class);
			
			for(Map<String, Object> data : gridData) {
				if((int)1 < (int)(Double.parseDouble(data.get("ROW_NO").toString()))) {
					SqlPack	sp = new SqlPack();
					String sqlText = "UP_WHR_Z_NHAM_EVA_RSLT_LS1_CHECK_S";
					sp.setStoreProcedure(true);
					sp.setSqlText(sqlText);
					String no_itemseq = data.get("NO_ITEMSEQ").toString();
					sp.getInParameters().put("P_CD_COMPANY",  companyCode);
					sp.getInParameters().put("P_YM_EVLU",   ym_evlu);
					sp.getInParameters().put("P_CD_EVUNIT",   cd_evunit);
					sp.getInParameters().put("P_TP_EVLU",   tp_evlu);
					sp.getInParameters().put("P_CD_EVGROUP",  cd_evgroup);
					sp.getInParameters().put("P_NO_EMP",   no_emp);
					sp.getInParameters().put("P_NO_ITEMSEQ",   no_itemseq.substring(0, no_itemseq.length()-2));
					sp.getInParameters().put("P_CD_SCALEH",	data.get("CD_SCALEH"));
					sp.getInParameters().put("P_CD_RSLT",	data.get("CD_RSLT"));
					sp.getInParameters().put("P_DC_RSLT",	data.get("DC_RSLT"));				
	
			        dt = this.getDataManager().queryForDataTable(sp);
			        changeCnt += Integer.parseInt(StringUtil.convertToString(dt.getData(0, "CHANGE_CNT")));
				}
			}
			
		}catch(Exception e){
			throw new DzApplicationRuntimeException(e);
		}
		return changeCnt;	
	}
	
	private int CheckDeadline(DzRequestModel dzRequestModel, String companyCode){
		DataTable dt = null;
		int deadlineCnt = 0;
		
		try{
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_evunit = requestModel.getParameterValue("cd_evunit");
			String tp_evlu = requestModel.getParameterValue("tp_evlu");
			String cd_evgroup = requestModel.getParameterValue("cd_evgroup");
			String no_emp = requestModel.getParameterValue("no_emp"); //조회조건에서 선택된 사원번호
			
			SqlPack	sp = new SqlPack();
			String sqlText = "UP_WHR_Z_NHAM_EVA_RSLT_LS1_DEADLINE_CHECK_S";
			sp.setStoreProcedure(true);
			sp.setSqlText(sqlText);
			sp.getInParameters().put("P_CD_COMPANY",  companyCode);
			sp.getInParameters().put("P_YM_EVLU",   ym_evlu);
			sp.getInParameters().put("P_CD_EVUNIT",   cd_evunit);
			sp.getInParameters().put("P_TP_EVLU",   tp_evlu);
			sp.getInParameters().put("P_CD_EVGROUP",  cd_evgroup);
			sp.getInParameters().put("P_NO_EMP",   no_emp);			

	        dt = this.getDataManager().queryForDataTable(sp);
	        deadlineCnt += Integer.parseInt(StringUtil.convertToString(dt.getData(0, "DEADLINE_CNT")));
			
		}catch(Exception e){
			throw new DzApplicationRuntimeException(e);
		}
		return deadlineCnt;	
	}
	
	@Transactional
	private boolean DeadLineUpdate(DzRequestModel requestModel, String companyCode) throws Exception {
		DbTransaction trans = null;
		
		try {
			trans = this.getDataManager().beginTransaction();
			String id_user = this.getUserId();
			
			String ym_evlu = requestModel.getParameterValue("ym_evlu");
			String cd_evunit = requestModel.getParameterValue("cd_evunit");
			String tp_evlu = requestModel.getParameterValue("tp_evlu");
			String cd_evgroup = requestModel.getParameterValue("cd_evgroup");
			String yn_deadline = requestModel.getParameterValue("yn_deadline");
			String no_emp = requestModel.getParameterValue("no_emp"); //조회조건에서 선택된 사원번호	
	
			List<Map<String, Object>> gridData = JsonUtil.deserialize(requestModel.getParameterValue("gridData"), ArrayList.class);
			
			for(Map<String, Object> data : gridData) {
				if((int)1 < (int)(Double.parseDouble(data.get("ROW_NO").toString()))) {
					SqlPack so = new SqlPack();
					String procedure = "UP_WHR_Z_NHAM_EVA_RSLT_LS1_YN_DEADLINE_U";
					so.setStoreProcedure(true);
					so.setSqlText(procedure);	
					String no_itemseq = data.get("NO_ITEMSEQ").toString();
					so.getInParameters().put("P_CD_COMPANY",  companyCode);
					so.getInParameters().put("P_YM_EVLU",   ym_evlu);
					so.getInParameters().put("P_CD_EVUNIT",   cd_evunit);
					so.getInParameters().put("P_TP_EVLU",   tp_evlu);
					so.getInParameters().put("P_CD_EVGROUP",   cd_evgroup);
					so.getInParameters().put("P_NO_EMP",  no_emp);
					so.getInParameters().put("P_NO_ITEMSEQ",   no_itemseq.substring(0, no_itemseq.length()-2));
					so.getInParameters().put("P_YN_DEADLINE",  yn_deadline);
					so.getInParameters().put("P_ID_UPDATE",   id_user);
					this.update(so);
				}
			}
			trans.commit();
		
		}catch(Exception e){
			if(trans != null){
				trans.rollback();
			}
			throw new DzApplicationRuntimeException(e);
		}
		return true;
	}
	
}
