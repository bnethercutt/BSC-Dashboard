package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;
import com.netsteadfast.greenstep.base.model.EntityUK;

@Entity
@Table(
		name="bb_workspace_config", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"SPACE_ID", "COMP_ID"} ) 
		} 
)
public class BbWorkspaceConfig extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 8356062992389821104L;
	private String oid;
	private String spaceId;	
	private String compId;
	private int position;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;	
	
	@Override
	@Id
	@EntityPK(name="oid")
	@Column(name="OID")
	public String getOid() {
		return oid;
	}
	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}	
	
	@EntityUK(name="spaceId")
	@Column(name="SPACE_ID")
	public String getSpaceId() {
		return spaceId;
	}
	
	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}
	
	@EntityUK(name="compId")
	@Column(name="COMP_ID")
	public String getCompId() {
		return compId;
	}
	
	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	@Column(name="POSITION")
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	@Override
	@Column(name="CUSERID")
	public String getCuserid() {
		return this.cuserid;
	}
	@Override
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	@Override
	@Column(name="CDATE")
	public Date getCdate() {
		return this.cdate;
	}
	@Override
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	@Override
	@Column(name="UUSERID")
	public String getUuserid() {
		return this.uuserid;
	}
	@Override
	public void setUuserid(String uuserid) {
		this.uuserid = uuserid;
	}
	@Override
	@Column(name="UDATE")
	public Date getUdate() {
		return this.udate;
	}
	@Override
	public void setUdate(Date udate) {
		this.udate = udate;
	}		

}
