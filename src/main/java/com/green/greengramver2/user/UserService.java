package com.green.greengramver2.user;

import com.green.greengramver2.common.ResultResponse;
import com.green.greengramver2.common.model.MyFileUtils;
import com.green.greengramver2.user.model.UserSignInReq;
import com.green.greengramver2.user.model.UserSignInRes;
import com.green.greengramver2.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;


    public int insUser(UserSignUpReq p, MultipartFile pic) {

        String password = BCrypt.hashpw(p.getUpw(),BCrypt.gensalt());
        p.setUpw(password);
        String savedPicName = null;
        int result = mapper.insUser(p);

        if(pic != null){
            savedPicName = myFileUtils.makeRandomFileName(pic);
        }
        p.setPic(savedPicName);

        if(pic == null) {
            return result;
        }

        // D:\2024-02\download\greengram_ver2/user/1/ahjsgdafsd.jpg
        String middleName = String.format("user/%s", p.getUserId());
        myFileUtils.makeFolders(middleName);

        try {
            myFileUtils.transferTo(pic,middleName + "/" + savedPicName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public UserSignInRes selUserByUid (UserSignInReq p){
        UserSignInRes res = mapper.selUserByUid(p.getUid());
        if (res == null){
            res = new UserSignInRes();
           res.setMessage("아이디를 확인해 주세요~");
           return res;
        }

        boolean trueId = BCrypt.checkpw(p.getUpw(), res.getUpw());
        if (!trueId){
            res = new UserSignInRes();
            res.setMessage("비밀번호가 틀린 것 같습니다~");
            return res;
        }
        res.setMessage("로그인 성공!");
        return res;
    }

}
