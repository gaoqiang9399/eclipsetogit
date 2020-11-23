</c:forEach items="${keepInfoList}" varStatus="statu">
						<tr class="<c:if test='#statu.even'>nth-child-even</c:if><c:if>nth-child-odd</c:if>">
							<td class="td-first">
								<div class="text-right">
									<p class="marginNone">${operateDate.substring(0,4)+'-'+operateDate.substring(4,6)+'-'+operateDate.substring(6,8)}</p>
								</div>
							</td>
							<td class="td-second">
								<div  class="td-second-div">
									<span class="title-color">${keepTypeName}</span>
									<span class="margin-left-20px">保管人：${keeperName}</span>
									<span class="margin-left-20px">经办人：${operatorCusName}</span>
									<span class="margin-left-20px">负责人：${dutyCusName}</span>
								</div>
								<p mytitle="keepDesc">
								  <c:choose>
										<c:when test="${keepDesc.length()>100}">
											${keepDesc.substring(0,100)}......
										</c:when>
										<c:otherwise>
											${keepDesc}
										</c:otherwise>
									</c:choose>		
								</p>
							</td>
						</tr>
					</c:forEach>