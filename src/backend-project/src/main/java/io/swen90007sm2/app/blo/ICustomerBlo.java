package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;

import javax.servlet.http.HttpServletRequest;

public interface ICustomerBlo {

    /**
     * Get JWT token if the loginParam is correct
     * @param loginParam loginParam from controller
     * @return valid token. If null. loginParam is not valid
     */
    AuthToken doLoginAndGenToken(LoginParam loginParam);

    /**
     * logout current customer
     * @param authToken token obj
     */
    void doLogout(AuthToken authToken);

    /**
     * get userInfo bean from db with token
     * @param tokenString token string
     * @return Customer bean
     */
    Customer getUserInfoBasedOnToken(String tokenString);

    /**
     * register a new user
     * @param registerParam param
     */
    void doRegisterUser(UserRegisterParam registerParam);

    /**
     * Identity Map's cache-Aside implementation
     * <br/>
     * if the data exists, get from cache,
     * if not, get from db.
     * <br/>
     * data in the cache will be expired to guarantee data eventually consistent
     * <br/>
     * using synchronized to prevent Cache Penetration, guarantee only one thread can update the cache,
     * rather than multiple threads rushed to query database and refresh cache again and again.
     *
     * @param userId customer's userId
     * @return customer object
     */
    Customer getUserInfoBasedByUserId(String userId);

    /**
     * find customers by page
     * @param pageNo page number required
     * @param pageSize num rows in one page
     * @return
     */
    PageBean<Customer> getCustomerByPage(int pageNo, int pageSize);

    /**
     * update user info, except password.
     * will check request token to find target customer to modify on
     */
    void doUpdateUserExceptPassword(String userId, UserUpdateParam param);

    /**
     * update customer pwd
     * @param userId current login user id
     * @param originalPassword original pwd
     * @param newPassword new pwd
     */
    void doUpdateUserPassword(String userId, String originalPassword, String newPassword);
}
