package org.example.utils;

import org.example.data.models.*;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import java.time.LocalDateTime;

public class Mapper {

    public static Resident mapToResident(RegisterResidentRequest request) {
        Resident resident = new Resident();
        resident.setName(toSentenceCase(request.getName()));
        resident.setEmail(toLowerCaseEmail(request.getEmail()));
        resident.setPhone(request.getPhone());
        resident.setAddress(request.getAddress());
        resident.setPassword(request.getPassword());
        return resident;
    }

    public static RegisterResidentResponse mapToRegisterResidentResponse(Resident resident) {
        RegisterResidentResponse response = new RegisterResidentResponse();
        response.setId(resident.getId());
        response.setName(resident.getName());
        response.setEmail(resident.getEmail());
        return response;
    }

    public static Security mapToSecurity(RegisterSecurityRequest request) {
        Security security = new Security();
        security.setName(toSentenceCase(request.getName()));
        security.setEmail(toLowerCaseEmail(request.getEmail()));
        security.setPassword(request.getPassword());
        return security;
    }

    public static RegisterSecurityResponse mapToRegisterSecurityResponse(Security security) {
        RegisterSecurityResponse response = new RegisterSecurityResponse();
        response.setId(security.getId());
        response.setName(security.getName());
        response.setEmail(security.getEmail());
//        response.setRole("SECURITY");
        return response;
    }

    public static Visitor mapToVisitor(GenerateAccessCodeRequest request) {
        Visitor visitor = new Visitor();
        visitor.setName(toSentenceCase(request.getVisitorName()));
        visitor.setEmail(toLowerCaseEmail(request.getVisitorEmail()));
        visitor.setPhone(request.getVisitorPhone());
        return visitor;
    }

    public static AccessCode mapToAccessCode(GenerateAccessCodeRequest request, String code, Resident resident, Visitor visitor) {
        AccessCode accessCode = new AccessCode();
        accessCode.setCode(code);
        accessCode.setResident(resident);
        accessCode.setCreatedAt(LocalDateTime.now());
        accessCode.setExpiresAt(LocalDateTime.now().plusHours(request.getDurationInHours()));
        accessCode.setUsed(false);
        accessCode.setWhomToSee(toSentenceCase(request.getWhomToSee()));
        accessCode.setVisitor(visitor);
        return accessCode;
    }

    public static GenerateAccessCodeResponse mapToGenerateAccessCodeResponse(AccessCode accessCode) {
        GenerateAccessCodeResponse response = new GenerateAccessCodeResponse();
        response.setAccessCode(accessCode.getCode());
        response.setResidentId(accessCode.getResident().getId());
        response.setExpiresAt(accessCode.getExpiresAt());
        response.setWhomToSee(accessCode.getWhomToSee());
        response.setVisitor(mapToVisitorResponse(accessCode.getVisitor()));
        return response;
    }

    public static FindAccessCodeResponse mapToFindAccessCodeResponse(AccessCode accessCode) {
        FindAccessCodeResponse response = new FindAccessCodeResponse();
        response.setResidentId(accessCode.getResident().getId());
        response.setCode(accessCode.getCode());
        response.setExpiresAt(accessCode.getExpiresAt());
        response.setUsed(accessCode.isUsed());
        response.setWhomToSee(accessCode.getWhomToSee());
        response.setVisitor(mapToVisitorResponse(accessCode.getVisitor()));
        return response;
    }

    public static LoginResponse mapToLoginResponse(Resident resident) {
        LoginResponse response = new LoginResponse();
        response.setId(resident.getId());
        response.setEmail(resident.getEmail());
        response.setRole("RESIDENT");
        return response;
    }

    public static LoginResponse mapToLoginResponse(Security security) {
        LoginResponse response = new LoginResponse();
        response.setId(security.getId());
        response.setEmail(security.getEmail());
        response.setRole("SECURITY");
        return response;
    }

    private static VisitorResponse mapToVisitorResponse(Visitor visitor) {
        if (visitor == null) return null;

        VisitorResponse visitorResponse = new VisitorResponse();
        visitorResponse.setId(visitor.getId());
        visitorResponse.setName(visitor.getName());
        visitorResponse.setEmail(visitor.getEmail());
        visitorResponse.setPhone(visitor.getPhone());
        return visitorResponse;
    }

    public static String toLowerCaseEmail(String email) {
        if (email != null) {
            return email.toLowerCase();
        }
        return null;
    }

    public static String toSentenceCase(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        String[] words = input.trim().split("\\s+");
        String result = "";
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                String word = words[i].toLowerCase();
                result += Character.toUpperCase(word.charAt(0)) + word.substring(1);
                if (i < words.length - 1) {
                    result += " ";
                }
            }
        }
        return result;
    }


}