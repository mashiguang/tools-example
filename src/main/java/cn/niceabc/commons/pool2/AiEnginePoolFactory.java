package cn.niceabc.commons.pool2;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class AiEnginePoolFactory extends BasePooledObjectFactory<AiEngine> {

    static GenericObjectPool<AiEngine> pool = null;

    public synchronized static GenericObjectPool<AiEngine> getInstance() {
        if (pool == null) {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxIdle(-1);
            config.setMinIdle(100);
            config.setMaxTotal(3);
            config.setLifo(false);
            pool = new GenericObjectPool<AiEngine>(new AiEnginePoolFactory(), config);
        }
        return pool;
    }

    public static AiEngine borrowObj() throws Exception {
        return AiEnginePoolFactory.getInstance().borrowObject();
    }

    public static void returnObj(AiEngine engine) {
        AiEnginePoolFactory.getInstance().returnObject(engine);
    }

    public static void close() {
        AiEnginePoolFactory.getInstance().close();
    }

    public static void clear() {
        AiEnginePoolFactory.getInstance().clear();
    }

    @Override
    public AiEngine create() throws Exception {
        return new AiEngine();
    }

    @Override
    public PooledObject<AiEngine> wrap(AiEngine obj) {
        return new DefaultPooledObject<AiEngine>(obj);
    }


}
