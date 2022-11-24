package com.dut.team92.userservice.constants;

import com.dut.team92.common.security.model.CustomUserPrincipal;
import com.dut.team92.userservice.configuration.properties.TokenProperties;
import com.dut.team92.userservice.domain.dto.request.LoginUserRequest;
import com.dut.team92.userservice.domain.entity.TokenPair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TestConstants {
    public static String  USER_NAME = "levanket";
    public static String PASSWORD = "Ket@1512001";
    public static String FULL_NAME = "Le Van Ket";

    public static LoginUserRequest VALID_USER_LOGIN = new LoginUserRequest(USER_NAME, PASSWORD);
    public static LoginUserRequest INVALID_USER_LOGIN = new LoginUserRequest("invalid-user", "wrong-password");
    public static Collection<? extends GrantedAuthority> roles = new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    public static CustomUserPrincipal USER_PRINCIPAL = new CustomUserPrincipal(USER_NAME, PASSWORD, "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            FULL_NAME, true, roles);
    public static Authentication AUTHENTICATION = new UsernamePasswordAuthenticationToken(USER_PRINCIPAL, PASSWORD, USER_PRINCIPAL.getAuthorities());
    public static String VALID_ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0YW5jY2toYW5oIiwiYXV0aCI6IlJPTEVfVVNFUiIsInRva2VuX3R5cGUiOiJBQ0NFU1MiLCJmdWxsX25hbWUiOiIiLCJzdWJfaWQiOiIwNzk2YjZhYS0zNzQ3LTQxYmQtODIzYi00OWViMzFlMmJkYTUiLCJleHAiOjE2Njc1NjMxNTh9.MDBdCIG0bDMDpmcpnwYn7qfPkCL-n53NylIzQZrwBUxyS5Pw0GDZKH-2zrC96v_ENT3v27O9tR_jt53uD_0DX8ZHDDX-uqUPreUh77Q2FiOCh4pKHuJ4rbA15YkyntmpHwoTFeP-9xTwvRRl11f-4-CApxFhJOnNOpskLZB3sOO3E0vblgQGEAf9VDHRyIEzzaucBBuGKiw8ioeGw04jHc7wLpe5bi91pTnIz6II1_Ly7Ea649494L3QNgjtTshqxM-S4KoQ_pdyr2k7gLsMK0fG2b-rQdYT6S-yz5sdL-hWnKFORtr_eIBce9vMWhidprDl9Mc1cbIS74pK4LMOig";
    public static String VALID_REFRESH_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0YW5jY2toYW5oIiwiYXV0aCI6IlJPTEVfVVNFUiIsInRva2VuX3R5cGUiOiJSRUZSRVNIIiwiZnVsbF9uYW1lIjoiIiwic3ViX2lkIjoiMDc5NmI2YWEtMzc0Ny00MWJkLTgyM2ItNDllYjMxZTJiZGE1IiwiZXhwIjoxNjY3NTY0NTk4fQ.ZkY_2EpfFCu-GEYQESAebMfWs85PQ7RzF5MK9P2NunwQIVO-32j_I93ikfDQ3yr7gZygg0Dr2bWOGX0gf6d3Vjfb6BYGE23mz4Hq3bfnMhvAALby4gIBDFr0DfEO3vNT2lV_RWLkf12-WgYm_aUkl2BAJmwvoa0PoigUPqCv_pw0MbQf-I3r5GgzfmVg9nxEbTqOKG-s5e8wisUWOj9o_qn0POD5UaXmlDLV_rIt_DR4teO-sqyG0ZqHo7weLL_ThqXCLJs8z9prLxh_XpLt2vV36KMjvoYqR-Up36O5VqGq0f5MOlV-TxE0DnDC9V-jb_o69zn4gRCksRDBtjJVLw";
    public static TokenPair TOKEN_PAIR = new TokenPair(VALID_ACCESS_TOKEN, VALID_REFRESH_TOKEN);
    public static TokenProperties TOKEN_PROPERTIES = new TokenProperties(360L, 1800L);
}

