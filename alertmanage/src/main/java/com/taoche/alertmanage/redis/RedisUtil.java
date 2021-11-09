package com.taoche.alertmanage.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public final class RedisUtil {

    @Autowired
    @Qualifier("alertRedisTemplate")
    private RedisTemplate<String, Object> alertRedisTemplate;

    /**
     * 获取redis获取操作对像
     *
     * @return
     */
    public ValueOperations getValueOperations() {
        ValueOperations valueOperations = this.alertRedisTemplate.opsForValue();
        return valueOperations;
    }

    /**
     * 高可用指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(毫秒)
     * @return
     */
    public Boolean expireHa(String key, Long time) {
        try {
            if (time > 0) {
                this.alertRedisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
            }
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 高可用根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getHaExpire(String key) {
        try {
            return this.alertRedisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 高可用判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasHaKey(String key) {
        try {
            return this.alertRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void delHa(List<String> keys) {
        try {
            if (keys != null && keys.size() > 0) {
                if (keys.size() == 1) {
                    this.alertRedisTemplate.delete(keys.get(0));
                } else {
                    this.alertRedisTemplate.delete(keys);
                }
            }
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
        }
    }

    // ============================high availability String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object getHa(String key) {
        try {
            Object value = (key == null) ? null : this.alertRedisTemplate.opsForValue().get(key);
            return value;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public Boolean setHa(String key, Object value) {
        try {
            this.alertRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(毫秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public Boolean setHa(String key, Object value, Long time) {
        try {
            if (time > 0) {
                this.alertRedisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
            } else {
                setHa(key, value);
            }
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public Long incrHa(String key, Long delta) {
        try {
            if (delta < 0) {
                throw new RuntimeException("递增因子必须大于0");
            }
            return this.alertRedisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }

    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public Long decrHa(String key, Long delta) {
        try {
            if (delta < 0) {
                throw new RuntimeException("递减因子必须大于0");
            }
            return this.alertRedisTemplate.opsForValue().increment(key, -delta);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }

    }

    // ================================high availability Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hgetHa(String key, String item) {
        try {
            return this.alertRedisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmgetHa(String key) {
        try {
            return this.alertRedisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public Boolean hmsetHa(String key, Map<String, Object> map) {
        try {
            this.alertRedisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(毫秒)
     * @return true成功 false失败
     */
    public Boolean hmsetHa(String key, Map<String, Object> map, Long time) {
        try {
            this.alertRedisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hsetHa(String key, String item, Object value) {
        try {
            this.alertRedisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public Boolean hsetHa(String key, String item, Object value, Long time) {
        try {
            this.alertRedisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                this.expireHa(key, time);
            }
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */

    public void hdelHa(String key, Object... item) {
        try {
            this.alertRedisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
        }

    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hHasHaKey(String key, String item) {
        try {
            return this.alertRedisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public Double hincrHa(String key, String item, Double by) {
        try {
            return this.alertRedisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public Double hdecrHa(String key, String item, Double by) {
        try {
            return this.alertRedisTemplate.opsForHash().increment(key, item, -by);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }
    // ============================high availability set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGetHa(String key) {
        try {
            return this.alertRedisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasHaKey(String key, Object value) {
        try {
            return this.alertRedisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSetHa(String key, Object... values) {
        try {
            return this.alertRedisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSetAndTimeHa(String key, Long time, Object... values) {
        try {
            Long count = this.alertRedisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                this.expireHa(key, time);
            }
            return count;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long sGetSetSizeHa(String key) {
        try {
            return this.alertRedisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemoveHa(String key, Object... values) {
        try {
            Long count = this.alertRedisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }
    // ===============================high availability list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGetHa(String key, Long start, Long end) {
        try {
            return this.alertRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long lGetListSizeHa(String key) {
        try {
            return this.alertRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndexHa(String key, Long index) {
        try {
            return this.alertRedisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean lSetHa(String key, Object value) {
        try {
            this.alertRedisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(毫秒)
     * @return
     */
    public Boolean lSetHa(String key, Object value, Long time) {
        try {
            this.alertRedisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public Boolean expire(String key, Long time) {
        try {
            if (time > 0) {
                this.alertRedisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
            }
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean lSetHa(String key, List<Object> value) {
        try {
            this.alertRedisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public Boolean lSetHa(String key, List<Object> value, Long time) {
        try {
            this.alertRedisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                this.expireHa(key, time);
            }
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public Boolean lUpdateIndexHa(String key, Long index, Object value) {
        try {
            this.alertRedisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemoveHa(String key, Long count, Object value) {
        try {
            Long remove = this.alertRedisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.info("redis 操作异常：" + e.getMessage());
            return null;
        }
    }
}