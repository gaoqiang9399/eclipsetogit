package com.zgcdb.demo.admin.utils;
import com.zgcdb.demo.admin.entity.ExcelUserEntity;
import com.zgcdb.demo.admin.utils.ExcelReaderUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Setter
@Getter
@ToString
public class UserTest {
    private String username;
    private String password;
    private String email;

    public static void main(String[] args) throws Exception {
        File file = new File("E:\\users.xlsx");
        List<ExcelUserEntity> excelUserEntities = ExcelReaderUtil.readExcel2Bean(new FileInputStream(file), ExcelUserEntity.class);
        
        for (ExcelUserEntity excelUserEntity : excelUserEntities) {
            System.out.println(excelUserEntity);
        }
    }
}