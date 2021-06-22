package edu.whut.cs.jee.mooc.client;

import edu.whut.cs.jee.mooc.upms.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author qixin on 2021/6/19.
 * @version 1.0
 */
@FeignClient(value = "user-server")
public interface UserClient extends UserApi {
}
