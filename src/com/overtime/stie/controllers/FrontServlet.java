package com.overtime.stie.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.overtime.stie.dao.AccessabilityDao;
import com.overtime.stie.dao.OverTimeDao;
import com.overtime.stie.dto.OverTimeRemarks;
import com.overtime.stie.dto.Overtime;
import com.overtime.stie.dto.User;
import com.overtime.stie.utilities.OvertimeUtilities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/FrontServlet")
public class FrontServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AccessabilityDao userdao;
    
    public OverTimeDao overtimedao;
    
   

	// assumes the current class is called MyLogger
	private final static Logger LOGGER = Logger.getLogger(FrontServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontServlet() {
        super();
        this.userdao = new AccessabilityDao();
        this.overtimedao = new OverTimeDao();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("======Entering doPost() of  LoginServlet==========");
		HttpSession session = request.getSession();

		String formaction = "";
		String btnaction = "";
		String userRole = "";
		boolean isError = false;
		String nxtPage = "";
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			formaction  = request.getParameter("formaction")==null?"":request.getParameter("formaction");
			btnaction = request.getParameter("btnaction")==null?"":request.getParameter("btnaction");
			
			System.out.println("formaction: " + formaction);
			
			System.out.println("btnaction: " + btnaction);

			
			if(!formaction.isEmpty() && !btnaction.isEmpty()) {
				
				if(btnaction.equalsIgnoreCase("continue")) {
					if(formaction.equalsIgnoreCase("registeration")) {
						request.setAttribute("generateOTP", true);
						nxtPage = "/jsp/register.jsp";
					}else if(formaction.equalsIgnoreCase("login")) {
						session.removeAttribute("role");
						session.removeAttribute("loginuser");
						session.removeAttribute("overtimesearch");
						session.removeAttribute("technicianList");
						session.removeAttribute("manangerList");
		                session.removeAttribute("depList");

						nxtPage = "/jsp/login.jsp";
					}
					else if(formaction.equalsIgnoreCase("dashboard")) {
						String nric = OvertimeUtilities.retrieveParam(request, "nric"); 
						String password = OvertimeUtilities.retrieveParam(request, "password");
						password = OvertimeUtilities.hashPassowrd(password);
						
						try {
							if(userdao.isUserValid(nric, password)) {
								session.setAttribute("loginuser", nric);
								userRole = userdao.findRole(nric);
								session.setAttribute("role", userRole);
								session.setAttribute("technicianList", userdao.retrieveAssets("technician"));
				                session.setAttribute("depList", userdao.retrieveAssets("department"));
				                session.setAttribute("manangerList", userdao.retrieveManagers());
								System.out.println("User_Role on login "+ userRole);
								
								if(userRole.equalsIgnoreCase("ENGINEER")) {
				                    request.setAttribute("hideSiteDiv", true);
				                    request.setAttribute("hideWoDiv", true);
				                    request.setAttribute("hideBreakDownType", true);
				                   

                                    nxtPage = "jsp/createdashboard.jsp";

								}else if(userRole.equalsIgnoreCase("MANAGER")) {
									List<Overtime> overtimeList =  overtimedao.retrieveOTList(nric, userRole);
									request.setAttribute("overtimeList", overtimeList);
									nxtPage = "jsp/viewdashboard.jsp";
								}else if(userRole.equalsIgnoreCase("HR")) {
									nxtPage = "jsp/search.jsp";
								}
							}else {
								request.setAttribute("USEREXIST", "false");
								nxtPage = "/jsp/login.jsp";
							}
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						
					}else if(formaction.equalsIgnoreCase("create")) {
						String user = OvertimeUtilities.retrieveParam(request, "name");
						System.out.println("===creating OT for the User "+ user + " ====");
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm");

						
						Overtime ot = new Overtime();

						String department = OvertimeUtilities.retrieveParam(request, "department");
						String startdate = OvertimeUtilities.retrieveParam(request, "startdate");
						String starttime = OvertimeUtilities.retrieveParam(request, "starttime");
						
						String enddate = OvertimeUtilities.retrieveParam(request, "enddate");
						String endtime = OvertimeUtilities.retrieveParam(request, "endtime");
						
						String purpose = OvertimeUtilities.retrieveParam(request, "purpose");
						
						String breakdown = OvertimeUtilities.retrieveParam(request, "breakdown");
						
						String site = OvertimeUtilities.retrieveParam(request, "site");
						
						String worder = OvertimeUtilities.retrieveParam(request, "worder");
						String breakdowntype = OvertimeUtilities.retrieveParam(request, "breakdowntype");
						
						String manager = OvertimeUtilities.retrieveParam(request, "manager");
						
						System.out.println("department "+ department + " startdate "+ startdate + " starttime " + starttime + " enddate " +  enddate
								+ " endtime " + endtime + " purpose " + purpose + " breakdown " + breakdown + " site " + site  + " workorder " + worder);
						Date sdate = sdf.parse(startdate+":"+starttime);
						Date edate = sdf.parse(enddate+":"+ endtime);
						
						if(edate.before(sdate)) {
							System.out.println("End date cannot be before start date ");
							request.setAttribute("createfail", "End date cannot be before start date ");
							request.setAttribute("hideSiteDiv", true);
			                request.setAttribute("hideWoDiv", true);
							request.setAttribute("hideBreakDownType", true);
							isError = true;
							
						}else {
							
							Calendar cal = new GregorianCalendar();
							cal.setTime(new Date());
							
							Calendar cal2 = new GregorianCalendar();
							cal2.setTime(sdate);
							
							Calendar cal3 = new GregorianCalendar();
							cal3.setTime(edate);
							
							System.out.println("OT start date  "+ cal2.getTime() + "today date: " + cal.getTime());

							if(breakdown.equalsIgnoreCase("yes")) {
								//Both start date and end date should be in past
								if(cal2.getTime().after(cal.getTime()) || cal3.getTime().after(cal.getTime())) {
									System.out.println("OT cannot be created for Breakdown cases if start and end dates are in present");
									request.setAttribute("createfail", "Dates are not valid, Make sure both dates are in past");
									request.setAttribute("hideWoDiv", true);
									request.setAttribute("hideBreakDownType", true);
									isError = true;
								}else {
									 cal2.add(Calendar.DAY_OF_WEEK, 7);
										
										
										if(cal2.getTime().before(cal.getTime()) || cal2.getTime().equals(cal.getTime())) {
											System.out.println("OT cannot be created for Breakdown cases which are more then 7 days old");
											request.setAttribute("createfail", "OT cannot be created Breakdown cases which are more then 7 days old");
											request.setAttribute("hideWoDiv", true);
											request.setAttribute("hideBreakDownType", true);
											isError = true;
										}else {
											isError = false;
											
										}
										
								}
								
							}else if(breakdown.equalsIgnoreCase("no")) {
								
                                if(cal2.getTime().before(cal.getTime())) {
									System.out.println("OT cannot be created for past dates for Onsite Cases");
									request.setAttribute("createfail", "OT cannot be created for past dates for Onsite Cases");
									request.setAttribute("hideSiteDiv", true);
									request.setAttribute("hideBreakDownType", false);
									if(!breakdowntype.equalsIgnoreCase("WorksOrder")) {
										request.setAttribute("hideWoDiv", true);

									}

									isError = true;
                                }else {
    								isError = false;

                                }
							
							}
								
								
						
						}
						
						System.out.println("department "+ department + " startdate "+ startdate + " starttime " + starttime + " enddate " +  enddate
								+ " endtime " + endtime + " purpose " + purpose + " breakdown " + breakdown + " site " + site  + " workorder " + worder);
						ot.setTechnicianName(user);
						ot.setDepartment(department);
						ot.setStartDate(startdate);
						ot.setStartTime(starttime);
						ot.setEndDate(enddate);
						ot.setEndTime(endtime);
						ot.setPurpose(purpose);
						ot.setBreakDown(breakdown);
						ot.setSite(site);
						ot.setWorkorderNumber(worder);
						String userfrom = (String) (session.getAttribute("loginuser")!=null?session.getAttribute("loginuser"):"");
						ot.setUserfrom(userfrom);
						ot.setBreakdowntype(breakdowntype);
						ot.setUserto(manager);
						
						
						if(!isError) {
							
							 int numberOfDays = OvertimeUtilities.calculateDaysBetween(startdate, enddate)+1;
						    
						    if(numberOfDays == -1) {
						    	throw new Exception("Date is Null");
						    }
						    System.out.println("Number of days: " + numberOfDays);
						    
						    int counter = 0;
						    for (int i = 0; i < numberOfDays; i++) {
						    	++counter;
						    	String calculatedDate = OvertimeUtilities.addDays(ot.getStartDate(), 1);
						    	System.out.println("calculated date :" + calculatedDate);
						    	if(calculatedDate.equalsIgnoreCase("-1")) {
						    		throw new Exception("Date is null");
						    	}
						    	ot.setStartDate(calculatedDate);
						    	ot.setEndDate(calculatedDate);
						    	//'03' send to manager
								ot.setStatus("03");
								overtimedao.save(ot);
	                        }
							request.setAttribute("hideSiteDiv", true);
			                request.setAttribute("hideWoDiv", true);
							request.setAttribute("hideBreakDownType", true);

			                request.setAttribute("count", counter);
							request.setAttribute("msg", "sucess");
							
							
							
						}else {
							request.setAttribute("overtime",ot);
							if(ot.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);


							}else if(ot.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								request.setAttribute("hideBreakDownType", false);
								if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							} 
						}
						
						nxtPage = "jsp/createdashboard.jsp";
					}else if(formaction.equalsIgnoreCase("history")) {
						String userfrom = (String) (session.getAttribute("loginuser")!=null?session.getAttribute("loginuser"):"");
						String role = (String) (session.getAttribute("role")!=null?session.getAttribute("role"):"");
						List<Overtime> overtimeList =  overtimedao.retrieveOTList(userfrom, role);
						request.setAttribute("overtimeList", overtimeList);
						request.setAttribute("page", formaction);
						nxtPage = "jsp/overtimehistory.jsp";
					}else if(formaction.equalsIgnoreCase("home")) {
						request.setAttribute("hideSiteDiv", true);
		                request.setAttribute("hideWoDiv", true);
	                    request.setAttribute("hideBreakDownType", true);

                        request.setAttribute("page", formaction);
						nxtPage = "jsp/createdashboard.jsp";
					}else if(formaction.equalsIgnoreCase("update")) {
						
						System.out.println("=====starting update ot method========");
						String user = OvertimeUtilities.retrieveParam(request, "name");

						String id = OvertimeUtilities.retrieveParam(request, "id");
						
						String department = OvertimeUtilities.retrieveParam(request, "department");
						String startdate = OvertimeUtilities.retrieveParam(request, "startdate");
						String starttime = OvertimeUtilities.retrieveParam(request, "starttime");
						
						String enddate = OvertimeUtilities.retrieveParam(request, "enddate");
						String endtime = OvertimeUtilities.retrieveParam(request, "endtime");
						
						String purpose = OvertimeUtilities.retrieveParam(request, "purpose");
						
						String breakdown = OvertimeUtilities.retrieveParam(request, "breakdown");
						
						String site = OvertimeUtilities.retrieveParam(request, "site");
						
						String worder = OvertimeUtilities.retrieveParam(request, "worder");
						
						String breakdowntype = OvertimeUtilities.retrieveParam(request, "breakdowntype");

						
						String manager = OvertimeUtilities.retrieveParam(request, "manager");
						
						System.out.println("id"+ id+ "department "+ department + " startdate "+ startdate + " starttime " + starttime + " enddate " +  enddate
								+ " endtime " + endtime + " purpose " + purpose + " breakdown " + breakdown + " site " + site  + " workorder " + worder + "manager" +manager);
						
					    int numberOfDays = OvertimeUtilities.calculateDaysBetween(startdate, enddate);
					    
					    if(numberOfDays >0) {
					    	Overtime ovtime = overtimedao.findOverTime(id);
							request.setAttribute("overtime", ovtime);
							if(ovtime.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);


							}else if(ovtime.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								request.setAttribute("hideBreakDownType", false);
								if(!ovtime.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							} 

							request.setAttribute("updatemsgfail", "fail");
							nxtPage = "jsp/createdashboard.jsp";

							

					    }else {
					    	Overtime ot = new Overtime();

							ot.setId(Integer.valueOf(id));
							ot.setTechnicianName(user);
							ot.setDepartment(department);
							ot.setStartDate(startdate);
							ot.setStartTime(starttime);
							ot.setEndDate(enddate);
							ot.setEndTime(endtime);
							ot.setPurpose(purpose);
							ot.setBreakDown(breakdown);
							
							if(breakdown.equalsIgnoreCase("yes")) {
								ot.setBreakdowntype("");
								worder ="";
							}else {
								ot.setBreakdowntype(breakdowntype);
								site = "";
							}
							ot.setSite(site);
							ot.setWorkorderNumber(worder);
							String userfrom = (String) (session.getAttribute("loginuser")!=null?session.getAttribute("loginuser"):"");
							ot.setUserfrom(userfrom);
							ot.setUserto(manager);
							//'03' send to manager
							ot.setStatus("03");
							
							overtimedao.updateOT(ot);
							
							Overtime ovtime = overtimedao.findOverTime(id);
							request.setAttribute("overtime", ovtime);
							if(ovtime.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);


							}else if(ovtime.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								request.setAttribute("hideBreakDownType", false);
								if(!ovtime.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							} 

							request.setAttribute("updatemsg", "sucess");
							nxtPage = "jsp/createdashboard.jsp";

					    	
					    }

						
					}else if(formaction.equalsIgnoreCase("search")) {
						request.setAttribute("page", "search");
						nxtPage = "jsp/search.jsp";
					}
					
				}else if(btnaction.equalsIgnoreCase("view")){
					
					String id = OvertimeUtilities.retrieveParam(request, "id");
					
					System.out.println("id to view "+  id);
					Overtime ot = overtimedao.findOverTime(id);
					request.setAttribute("overtime", ot);
					
					if(ot.getBreakDown().equalsIgnoreCase("yes")) {
						request.setAttribute("hideWoDiv", true);
						request.setAttribute("hideBreakDownType", true);


					}else if(ot.getBreakDown().equalsIgnoreCase("no")) {
						request.setAttribute("hideSiteDiv", true);
						request.setAttribute("hideBreakDownType", false);
						if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
							request.setAttribute("hideWoDiv", true);

						}

					}
					nxtPage = "jsp/createdashboard.jsp";

					
				}else if(btnaction.equalsIgnoreCase("accept")) {
					 String id = OvertimeUtilities.retrieveParam(request, "id"); 
					 if(overtimedao.update(id, "accept")) {
						request.setAttribute("updatemsgaccept", "sucess");
						Overtime ot = overtimedao.findOverTime(id);
						request.setAttribute("overtime", ot);
						if(ot.getBreakDown().equalsIgnoreCase("yes")) {
							request.setAttribute("hideWoDiv", true);
							request.setAttribute("hideBreakDownType", true);

                          }else if(ot.getBreakDown().equalsIgnoreCase("no")) {
							request.setAttribute("hideSiteDiv", true);
							if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
								request.setAttribute("hideWoDiv", true);

							}

						}  
                        nxtPage = "jsp/createdashboard.jsp";

					 }
					
				}else if(btnaction.equalsIgnoreCase("approve")) {
					 String id = OvertimeUtilities.retrieveParam(request, "id"); 
                     if(overtimedao.update(id, "approve")) {
							request.setAttribute("updatemsgaccept", "sucess");
							String userfrom = (String) (session.getAttribute("loginuser")!=null?session.getAttribute("loginuser"):"");
							String role = (String) (session.getAttribute("role")!=null?session.getAttribute("role"):"");
							List<Overtime> overtimeList =  overtimedao.retrieveOTList(userfrom, role);
							request.setAttribute("overtimeList", overtimeList);
							request.setAttribute("page", "history");
							nxtPage = "jsp/overtimehistory.jsp";

						 }
				}
				else if(btnaction.equalsIgnoreCase("reject")) {
					 String id = OvertimeUtilities.retrieveParam(request, "id");
					 String reason = OvertimeUtilities.retrieveParam(request, "remarkbyman");
					 
					 System.out.println("id: "+ id+ ", reason: "+  reason);
					 if(overtimedao.update(id, "reject")) {
							String role = (String) (session.getAttribute("role")!=null?session.getAttribute("role"):"");
							Overtime overtime = overtimedao.findOverTime(id);
							OverTimeRemarks otremarks = new OverTimeRemarks();
							otremarks.setRemarks(reason);
							otremarks.setOtId(Integer.valueOf(id));
							overtime.getRemarks().add(otremarks);
                            overtimedao.insertRemarks(overtime, role);
							request.setAttribute("updatemsgreject", "sucess");
							Overtime ot = overtimedao.findOverTime(id);
							request.setAttribute("overtime", ot);
							if(ot.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);

                              }else if(ot.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							}                        
			                nxtPage = "jsp/createdashboard.jsp";

						 }
				}else if("remarkByEngineer".equalsIgnoreCase(btnaction)) {
					 String id = OvertimeUtilities.retrieveParam(request, "id");
					 String reason = OvertimeUtilities.retrieveParam(request, "remarkbyeng");
					 
					 System.out.println("id: "+ id+ ", reason: "+  reason);
					 if(overtimedao.update(id, "remarkByEngineer")) {
							String role = (String) (session.getAttribute("role")!=null?session.getAttribute("role"):"");
							Overtime overtime = overtimedao.findOverTime(id);
							OverTimeRemarks otremarks = new OverTimeRemarks();
							otremarks.setRemarks(reason);
							otremarks.setOtId(Integer.valueOf(id));
							overtime.getRemarks().add(otremarks);
                            overtimedao.insertRemarks(overtime, role);
    						request.setAttribute("engineerremark", "sucess");
							Overtime ot = overtimedao.findOverTime(id);
							request.setAttribute("overtime", ot);
							if(ot.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);


							}else if(ot.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							}                         
			                nxtPage = "jsp/createdashboard.jsp";

						 }
					
				}else if(btnaction.equalsIgnoreCase("cancel")) {

					 String id = OvertimeUtilities.retrieveParam(request, "id");
					 String reason = OvertimeUtilities.retrieveParam(request, "cancel");
					 
					 System.out.println("id: "+ id+ ", reason: "+  reason);
					 if(overtimedao.update(id, "cancel")) {
							String role = (String) (session.getAttribute("role")!=null?session.getAttribute("role"):"");
							Overtime overtime = overtimedao.findOverTime(id);
							OverTimeRemarks otremarks = new OverTimeRemarks();
							otremarks.setRemarks(reason);
							otremarks.setOtId(Integer.valueOf(id));
							overtime.getRemarks().add(otremarks);
                            overtimedao.insertRemarks(overtime, role);
							request.setAttribute("updatemsgcancelformaanger", "sucess");
							Overtime ot = overtimedao.findOverTime(id);
							request.setAttribute("overtime", ot);
							if(ot.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);

                             }else if(ot.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							}                        
			                nxtPage = "jsp/createdashboard.jsp";

						 }
				
					
					
				}else if(btnaction.equalsIgnoreCase("approvecancel")) {
					 String id = OvertimeUtilities.retrieveParam(request, "id");
					 
					 System.out.println("id: "+ id);
					 if(overtimedao.update(id, "cancel")) {
							
							request.setAttribute("updatemsgapprovalcancel", "sucess");
							Overtime ot = overtimedao.findOverTime(id);
							request.setAttribute("overtime", ot);
							if(ot.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);

                             }else if(ot.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							}                        
			                nxtPage = "jsp/createdashboard.jsp";

						 }
					
				}
				else if(btnaction.equalsIgnoreCase("pendingcancel")) {
					
					 String id = OvertimeUtilities.retrieveParam(request, "id");
					 String reason = OvertimeUtilities.retrieveParam(request, "cancel");
					 
					 System.out.println("id: "+ id+ ", reason: "+  reason);
					 if(overtimedao.update(id, "pendingcancel")) {
							String role = (String) (session.getAttribute("role")!=null?session.getAttribute("role"):"");
							Overtime overtime = overtimedao.findOverTime(id);
							OverTimeRemarks otremarks = new OverTimeRemarks();
							otremarks.setRemarks(reason);
							otremarks.setOtId(Integer.valueOf(id));
							overtime.getRemarks().add(otremarks);
                            overtimedao.insertRemarks(overtime, role);
							request.setAttribute("updatemsgcancel", "sucess");
							Overtime ot = overtimedao.findOverTime(id);
							request.setAttribute("overtime", ot);
							if(ot.getBreakDown().equalsIgnoreCase("yes")) {
								request.setAttribute("hideWoDiv", true);
								request.setAttribute("hideBreakDownType", true);

                             }else if(ot.getBreakDown().equalsIgnoreCase("no")) {
								request.setAttribute("hideSiteDiv", true);
								if(!ot.getBreakdowntype().equalsIgnoreCase("WorksOrder")) {
									request.setAttribute("hideWoDiv", true);

								}

							}                        
			                nxtPage = "jsp/createdashboard.jsp";

						 }
				
					
				}
				else if(btnaction.equalsIgnoreCase("search")) {
					 String name = OvertimeUtilities.retrieveParam(request, "name");
					 String department = OvertimeUtilities.retrieveParam(request, "department");
					 String startdate = OvertimeUtilities.retrieveParam(request, "startdate");
					 String enddate = OvertimeUtilities.retrieveParam(request, "enddate");
					 String status = OvertimeUtilities.retrieveParam(request, "status");
					 String site = OvertimeUtilities.retrieveParam(request, "site");
					 String login = (String) (session.getAttribute("loginuser")!=null?session.getAttribute("loginuser"):"");
					 Overtime ot = new Overtime();
					 ot.setTechnicianName(name);
					 ot.setDepartment(department);
					 ot.setStartDate(startdate);
					 ot.setEndDate(enddate);
					 ot.setStatus(status);
					 ot.setSite(site);
					 ot.setUserto(login);
					 System.out.println("name: " + name + " department: "+ department + " startdate: " + startdate + " enddate: "+ enddate + " status "+ status + "site");
					 String role = (String) (session.getAttribute("role")!=null?session.getAttribute("role"):"");

					 List<Overtime> overtimeList = overtimedao.searchOvertime(ot, role);
/*					 request.setAttribute("overtimeList", overtimeList);
*/					 session.setAttribute("overtimesearch", overtimeList);
					 //Setting the values back in order to show in teh search page
					 ot.setTechnicianName(name);
					 ot.setDepartment(department);
					 ot.setStartDate(startdate);
					 ot.setEndDate(enddate);
					 ot.setSite(site);
					 request.setAttribute("ot", ot);
		             nxtPage = "jsp/search.jsp";

				}
				else if(btnaction.equalsIgnoreCase("generate")) {
					
					 String nric = OvertimeUtilities.retrieveParam(request, "nric");
					 System.out.println(nric);

			         User user = userdao.findUserByNRIC(nric);
			         
			       if(user!= null && (user.getRole().equalsIgnoreCase("ENGINEER") || user.getRole().equalsIgnoreCase("MANAGER") || user.getRole().equalsIgnoreCase("HR") )) {
			    	   if(user.getOtp()== null) {
				        	 System.out.println("Not generated OTP yet");
					        

				         }else {
				        	 System.out.println("OTP generated but trying again");
				         }
				         
				       

						 if(!user.getEmail().isEmpty()) {
							    String otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
								 System.out.println("OTP generated: " +otp);
					             OvertimeUtilities.sendMail(user.getEmail(), otp);
					             userdao.updateUser(nric,otp);
						         request.setAttribute("showOTP", true);
						         request.setAttribute("showRegister", true);
						         request.setAttribute("emailsent", true);
						         request.setAttribute("generateOTP", false);
						         request.setAttribute("nric", nric);

						 }
						 else {
							 System.out.println("Email is empty for "+ nric + " so will not generate OTP ");
							 request.setAttribute("generateOTP", true);
					         request.setAttribute("noemailfound", true);
							 
						 }
							 
				         
				       
			          }else {
				         request.setAttribute("userNotFound", true);
				         request.setAttribute("showOTP", false);
				         request.setAttribute("showRegister", false);
				         request.setAttribute("emailsent", false);
				         request.setAttribute("generateOTP", true);
				         request.setAttribute("nric", nric);

			           }
			        
			         nxtPage = "jsp/register.jsp";
					 

					
				}
				else if(btnaction.equalsIgnoreCase("register")) {
					 String nric = OvertimeUtilities.retrieveParam(request, "nric");
					 String otp = OvertimeUtilities.retrieveParam(request, "otp");
					 
					 String password = OvertimeUtilities.retrieveParam(request, "password");
					 
					 System.out.println("nric: "+ nric + "otp: " + otp + "password: " + password);
					 
					 if(password.equalsIgnoreCase("")) {
						 User user = userdao.findUserByNRIC(nric);
						 if(user!=null) {
							 if(user.getOtp().equalsIgnoreCase(otp)) {
								     request.setAttribute("showpassword", true);
								     request.setAttribute("showOTP", false);
							         request.setAttribute("showRegister", true);
							         request.setAttribute("emailsent", false);
							         request.setAttribute("generateOTP", false);

							 }else {
								 request.setAttribute("wrongotp", true);
								 
								 request.setAttribute("showpassword", false);
							     request.setAttribute("showOTP", true);
						         request.setAttribute("showRegister", true);
						         request.setAttribute("emailsent", false);
						         request.setAttribute("generateOTP", false);
								 

							 }
						 }
					 }else {
						 password = OvertimeUtilities.hashPassowrd(password);

						 userdao.setpassword(password, nric);
						 request.setAttribute("passwordsetsucess", true);
					 }
			         request.setAttribute("nric", nric);
                     nxtPage = "jsp/register.jsp";
					 
					
				}
				else if(btnaction.equalsIgnoreCase("export")) {
					 List<Overtime> overtimeList  = (List<Overtime>) session.getAttribute("overtimesearch");
					 OvertimeUtilities.exportExcel(overtimeList, response);
					 return;

					
				}
			}else {
				LOGGER.info("=======Empty parameters=====");
				nxtPage = "/jsp/error.jsp";
				isError = true;
			}
		}catch (Exception e) {
			LOGGER.info(e.getMessage());
			nxtPage = "/jsp/error.jsp";
			isError = true;
		}
		
		
		request.getRequestDispatcher(nxtPage).forward(request, response);

	}
	
	
	

}
