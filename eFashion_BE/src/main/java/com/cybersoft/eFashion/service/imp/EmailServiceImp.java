package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.dto.EmailDTO;

public interface EmailServiceImp {

    //Send mail text
    String sendSimpleMail(EmailDTO emailDTO);

    //Send mail attach file
    String sendMailWithAttachment(EmailDTO emailDTO);

}
