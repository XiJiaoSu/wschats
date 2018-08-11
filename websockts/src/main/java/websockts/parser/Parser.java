package websockts.parser;

/**
 *消息解析
 * @param <T>
 */
public interface Parser{

    public <T>T stringToObject(String content,Class<T> clazz);

    public <T>String ObjectToString(T object);

}
