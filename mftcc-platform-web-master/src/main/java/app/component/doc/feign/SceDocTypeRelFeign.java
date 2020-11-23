package app.component.doc.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.doc.entity.SceDocTypeRel;

@FeignClient("mftcc-platform-factor")
public interface SceDocTypeRelFeign {
	
	@RequestMapping(value = "/sceDocTypeRel/getSceDocTypeRels")
	public List<SceDocTypeRel> getSceDocTypeRels(@RequestBody SceDocTypeRel sceDocTypeRel) throws ServiceException;


}
