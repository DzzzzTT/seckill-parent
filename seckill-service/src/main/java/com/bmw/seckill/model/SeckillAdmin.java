package com.bmw.seckill.model;

import java.util.Date;


/**
 * 
 * 管理员姓名表
 * 
 **/
public class SeckillAdmin{


  /****/

  private Long id;


  /**登录名**/

  private String loginName;


  /**密码**/

  private String password;


  /**展示管理员名称**/

  private String name;


  /**ip范围，空不限制**/

  private String ipRange;


  /**创建时间**/

  private Date createTime;




  public void setId(Long id) { 
  }


  public Long getId() { 
  }


  public void setLoginName(String loginName) { 
  }


  public String getLoginName() { 
  }


  public void setPassword(String password) { 
  }


  public String getPassword() { 
  }


  public void setName(String name) { 
  }


  public String getName() { 
  }


  public void setIpRange(String ipRange) { 
  }


  public String getIpRange() { 
  }


  public void setCreateTime(Date createTime) { 
  }


  public Date getCreateTime() { 
  }

}