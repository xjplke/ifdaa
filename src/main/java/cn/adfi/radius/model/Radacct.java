package cn.adfi.radius.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the radacct database table.
 * 
 */
@Entity
@Table(name="radacct",indexes = {
	@Index(columnList = "username",name="username"),
	@Index(columnList = "framedipaddress",name="framedipaddress"),
	@Index(columnList = "acctsessionid",name="acctsessionid"),
	@Index(columnList = "acctsessiontime",name="acctsessiontime"),
	@Index(columnList = "acctuniqueid",name="acctuniqueid"),
	@Index(columnList = "acctstarttime",name="acctstarttime"),
	@Index(columnList = "acctstoptime",name="acctstoptime"),
	@Index(columnList = "nasipaddress",name="nasipaddress")
})
public class Radacct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false,columnDefinition="bigint(21)")
	private Long radacctid;

	@Column(length=32)
	private String acctauthentic;

	private BigInteger acctinputoctets;

	private BigInteger acctoutputoctets;

	@Column(nullable=false, length=64)
	
	private String acctsessionid;

	private int acctsessiontime;

	private int acctstartdelay;

	@Temporal(TemporalType.TIMESTAMP)
	private Date acctstarttime;

	private int acctstopdelay;

	@Temporal(TemporalType.TIMESTAMP)
	private Date acctstoptime;

	@Column(nullable=false, length=32)
	private String acctterminatecause;

	@Column(nullable=false, length=32)
	private String acctuniqueid;

	@Column(nullable=false, length=50)
	private String calledstationid;

	@Column(nullable=false, length=50)
	private String callingstationid;

	@Column(name="connectinfo_start", length=50)
	private String connectinfoStart;

	@Column(name="connectinfo_stop", length=50)
	private String connectinfoStop;

	@Column(nullable=false, length=15)
	private String framedipaddress;

	@Column(length=32)
	private String framedprotocol;

	@Column(nullable=false, length=64)
	private String groupname;

	@Column(nullable=false, length=15)
	private String nasipaddress;

	@Column(length=15)
	private String nasportid;

	@Column(length=32)
	private String nasporttype;

	@Column(length=64)
	private String realm;

	@Column(length=32)
	private String servicetype;

	@Column(nullable=false, length=64)
	private String username;

	@Column(length=10)
	private String xascendsessionsvrkey;

	public Radacct() {
	}

	public Long getRadacctid() {
		return this.radacctid;
	}

	public void setRadacctid(Long radacctid) {
		this.radacctid = radacctid;
	}

	public String getAcctauthentic() {
		return this.acctauthentic;
	}

	public void setAcctauthentic(String acctauthentic) {
		this.acctauthentic = acctauthentic;
	}

	public BigInteger getAcctinputoctets() {
		return this.acctinputoctets;
	}

	public void setAcctinputoctets(BigInteger acctinputoctets) {
		this.acctinputoctets = acctinputoctets;
	}

	public BigInteger getAcctoutputoctets() {
		return this.acctoutputoctets;
	}

	public void setAcctoutputoctets(BigInteger acctoutputoctets) {
		this.acctoutputoctets = acctoutputoctets;
	}

	public String getAcctsessionid() {
		return this.acctsessionid;
	}

	public void setAcctsessionid(String acctsessionid) {
		this.acctsessionid = acctsessionid;
	}

	public int getAcctsessiontime() {
		return this.acctsessiontime;
	}

	public void setAcctsessiontime(int acctsessiontime) {
		this.acctsessiontime = acctsessiontime;
	}

	public int getAcctstartdelay() {
		return this.acctstartdelay;
	}

	public void setAcctstartdelay(int acctstartdelay) {
		this.acctstartdelay = acctstartdelay;
	}

	public Date getAcctstarttime() {
		return this.acctstarttime;
	}

	public void setAcctstarttime(Date acctstarttime) {
		this.acctstarttime = acctstarttime;
	}

	public int getAcctstopdelay() {
		return this.acctstopdelay;
	}

	public void setAcctstopdelay(int acctstopdelay) {
		this.acctstopdelay = acctstopdelay;
	}

	public Date getAcctstoptime() {
		return this.acctstoptime;
	}

	public void setAcctstoptime(Date acctstoptime) {
		this.acctstoptime = acctstoptime;
	}

	public String getAcctterminatecause() {
		return this.acctterminatecause;
	}

	public void setAcctterminatecause(String acctterminatecause) {
		this.acctterminatecause = acctterminatecause;
	}

	public String getAcctuniqueid() {
		return this.acctuniqueid;
	}

	public void setAcctuniqueid(String acctuniqueid) {
		this.acctuniqueid = acctuniqueid;
	}

	public String getCalledstationid() {
		return this.calledstationid;
	}

	public void setCalledstationid(String calledstationid) {
		this.calledstationid = calledstationid;
	}

	public String getCallingstationid() {
		return this.callingstationid;
	}

	public void setCallingstationid(String callingstationid) {
		this.callingstationid = callingstationid;
	}

	public String getConnectinfoStart() {
		return this.connectinfoStart;
	}

	public void setConnectinfoStart(String connectinfoStart) {
		this.connectinfoStart = connectinfoStart;
	}

	public String getConnectinfoStop() {
		return this.connectinfoStop;
	}

	public void setConnectinfoStop(String connectinfoStop) {
		this.connectinfoStop = connectinfoStop;
	}

	public String getFramedipaddress() {
		return this.framedipaddress;
	}

	public void setFramedipaddress(String framedipaddress) {
		this.framedipaddress = framedipaddress;
	}

	public String getFramedprotocol() {
		return this.framedprotocol;
	}

	public void setFramedprotocol(String framedprotocol) {
		this.framedprotocol = framedprotocol;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getNasipaddress() {
		return this.nasipaddress;
	}

	public void setNasipaddress(String nasipaddress) {
		this.nasipaddress = nasipaddress;
	}

	public String getNasportid() {
		return this.nasportid;
	}

	public void setNasportid(String nasportid) {
		this.nasportid = nasportid;
	}

	public String getNasporttype() {
		return this.nasporttype;
	}

	public void setNasporttype(String nasporttype) {
		this.nasporttype = nasporttype;
	}

	public String getRealm() {
		return this.realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getServicetype() {
		return this.servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getXascendsessionsvrkey() {
		return this.xascendsessionsvrkey;
	}

	public void setXascendsessionsvrkey(String xascendsessionsvrkey) {
		this.xascendsessionsvrkey = xascendsessionsvrkey;
	}

}