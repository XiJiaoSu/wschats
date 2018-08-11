package websockts.container;

import java.io.Serializable;

public interface SessionService<T,PK extends Serializable> {

	/**
	 * 第一次连接过来，将session加入容器
	 * @param session
	 */
	void onLine(T session,PK id);


	/**
	 * 下线，依据id将指定的session从容器中移除
	 *
	 * @param id
	 */
	void offLine(PK id);

}
