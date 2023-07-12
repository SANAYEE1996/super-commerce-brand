package com.example.brand.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.brand.dto.BrandRegisterDto;
import com.example.brand.entity.Manager;
import com.example.brand.service.BrandService;
import com.example.brand.service.ImageSaveService;
import com.example.brand.service.ManagerService;
import com.example.brand.service.RoleService;
import com.example.brand.util.EntityConverter;
import com.example.brand.util.ManagerRole;
import com.example.brand.util.ResponseDto;
import com.example.brand.util.ResponseStatus;
import com.example.brand.util.exception.DuplicateBrandManagerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {

    private final AmazonS3Client amazonS3Client;

    private final ManagerService managerService;

    private final BrandService brandService;

    private final RoleService roleService;

    private final ImageSaveService imageSaveService;

    private final EntityConverter entityConverter;

    @Transactional
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto registerBrand(@RequestPart(name = "BrandRegisterInfo") BrandRegisterDto request
                                    ,@RequestPart(value = "brandImage", required = false) MultipartFile img){

        String fileName = img.getOriginalFilename() +"_"+ System.currentTimeMillis();

        try{
            Manager manager = managerService.findManager(request.getManagerEmail());
            validatePossibleBrandRegisterManager(manager.getRoles());
            imageSaveService.saveBrandImage(amazonS3Client,img,fileName);
            managerService.registerBrand(manager,brandService.findBrand(brandService.registerBrand(entityConverter.toBrandEntity(request, fileName))));
            roleService.updateRole(manager.getId(), ManagerRole.BRAND_MASTER.getRole());
        } catch (IOException e){
            e.printStackTrace();
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        } catch (DuplicateBrandManagerException e){
            log.error(e.getMessage());
            brandService.deleteBrand(e.getRemoveBrandId());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }

        return ResponseDto.builder().message("등록 성공").code(ResponseStatus.OK.getStatusCode()).build();
    }

    private void validatePossibleBrandRegisterManager(List<String> roleList) throws IOException {
        if(!(roleList.size() == 1 && roleList.contains(ManagerRole.NONE.getRole()))){
            throw new IOException("등록 할 수 없는 계정");
        }
    }
}
