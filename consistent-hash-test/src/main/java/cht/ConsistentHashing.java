package cht;

import java.util.*;

public class ConsistentHashing {

    // 虚拟节点 => 物理节点
    private final TreeMap<Long, String> virtualNodes = new TreeMap<>();

    private final int virtualCopies;

    // 根据物理节点，构建虚拟节点映射表
    public ConsistentHashing(Set<String> physicalNodes, int virtualCopies) {
        this.virtualCopies = virtualCopies;
        for (String nodeIp : physicalNodes) {
            addPhysicalNode(nodeIp);
        }
    }

    // 添加物理节点
    public void addPhysicalNode(String nodeIp) {
        for (int idx = 0; idx < virtualCopies; ++idx) {
            long hash = FNVHash(nodeIp + "#" + idx);
            virtualNodes.put(hash, nodeIp);
        }
    }

    // 删除物理节点
    public void removePhysicalNode(String nodeIp) {
        for (int idx = 0; idx < virtualCopies; ++idx) {
            long hash = FNVHash(nodeIp + "#" + idx);
            virtualNodes.remove(hash);
        }
    }

    // 查找对象映射的节点
    public String getObjectNode(String object) {
        long hash = FNVHash(object);
        SortedMap<Long, String> tailMap = virtualNodes.tailMap(hash); // 所有大于 hash 的节点
        Long key = tailMap.isEmpty() ? virtualNodes.firstKey() : tailMap.firstKey();
        return virtualNodes.get(key);
    }

    // 统计对象与节点的映射关系
    public Map<String, Long> dumpObjectOwnedByNode(long objectMin, long objectMax) {
        // 统计
        Map<String, Long> objectNodeMap = new TreeMap<>(); // IP => COUNT
        for (long object = objectMin; object < objectMax; ++object) {
            String nodeIp = getObjectNode(String.valueOf(object));
            Long count = objectNodeMap.get(nodeIp);
            objectNodeMap.put(nodeIp, (count == null ? 0 : count + 1));
        }
        return objectNodeMap;
    }

    private static class CHEntry {
        private final long hash;
        private final String ip;

        public CHEntry(long hash, String ip) {
            this.hash = hash;
            this.ip = ip;
        }

        public long getHash() {
            return hash;
        }

        public String getIp() {
            return ip;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CHEntry chEntry = (CHEntry) o;
            return hash == chEntry.hash && Objects.equals(ip, chEntry.ip);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hash, ip);
        }

        @Override
        public String toString() {
            return "[hash=" + hash + ", ip='" + ip + "]";
        }
    }

    public Set<CHEntry> dumpObjectToKey(long objectMin, long objectMax) {
        Set<CHEntry> set = new LinkedHashSet<>();
        for (long object = objectMin; object < objectMax; ++object) {
            String nodeIp = getObjectNode(String.valueOf(object));
            CHEntry chEntry = new CHEntry(object, nodeIp);
            set.add(chEntry);
        }
        return set;
    }

    // 32位的 Fowler-Noll-Vo 哈希算法，
    // https://en.wikipedia.org/wiki/Fowler–Noll–Vo_hash_function
    private static Long FNVHash(String key) {
        final int p = 16777619;
        long hash = 2166136261L;
        for (int idx = 0, num = key.length(); idx < num; ++idx) {
            hash = (hash ^ key.charAt(idx)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        // 物理节点
        Set<String> physicalNodes = new TreeSet<>();
        physicalNodes.add("192.168.1.101");
        physicalNodes.add("192.168.1.102");
        physicalNodes.add("192.168.1.103");
        physicalNodes.add("192.168.1.104");

        ConsistentHashing ch = new ConsistentHashing(physicalNodes, 10000);
//        testRate(ch);

        testStable(ch);

    }

    private static void testStable(ConsistentHashing ch) {

        System.out.println("=========== 移除103 ===========");
        Set<CHEntry> init4Nodes = ch.dumpObjectToKey(0L, 65535L);
        ch.removePhysicalNode("192.168.1.103");
        Set<CHEntry> remove103 = ch.dumpObjectToKey(0L, 65535L);
        printAffectedIp(init4Nodes, remove103);

//        System.out.println("=========== 加入108 ===========");
//        ch.addPhysicalNode("192.168.1.103");
//        init4Nodes = ch.dumpObjectToKey(0L, 65535L);
//        ch.addPhysicalNode("192.168.1.108");
//        Set<CHEntry> add108 = ch.dumpObjectToKey(0L, 65535L);
//        init4Nodes.removeAll(add108);
//        printAffectedIp(init4Nodes, add108);

    }

    private static void printAffectedIp(Set<CHEntry> before, Set<CHEntry> after) {
        int i = 0;
        Iterator<CHEntry> iterator = before.iterator();
        while (iterator.hasNext()) {
            CHEntry next = iterator.next();
            if (after.contains(next)) {
                iterator.remove();
                after.remove(next);
            }
        }

        for (CHEntry chEntry : before) {
            for (CHEntry chEntry1 : after) {
                if (chEntry.getHash() == chEntry1.getHash()) {
                    i++;
                    System.out.println(chEntry.getHash() + " => before:" + chEntry.getIp() + ", after:" + chEntry1.getIp());
                }
            }
        }
        System.out.println("total " + i + " hash's ip changed!");
    }

    private static void testRate(ConsistentHashing ch) {

        System.out.println("======== 初始四个节点 ========");
        printNodeRate(ch.dumpObjectOwnedByNode(0L, 65535L));

        // 删除物理节点
        System.out.println("======== 删除物理节点 ========");
        ch.removePhysicalNode("192.168.1.103");
        printNodeRate(ch.dumpObjectOwnedByNode(0, 65535L));

        // 添加物理节点
        System.out.println("======== 添加物理节点 ========");
        ch.addPhysicalNode("192.168.1.108");
        printNodeRate(ch.dumpObjectOwnedByNode(0, 65535L));
    }

    private static void printNodeRate(Map<String, Long> map) {
        if (map == null) {
            return;
        }

        Optional<Long> reduce = map.values().stream().reduce(Long::sum);

        long totalCount = reduce.orElse(0L) + map.size();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            long percent = (int) (100 * entry.getValue() / totalCount);
            System.out.println("IP=" + entry.getKey() + ": RATE=" + percent + "% (" + entry.getValue() + "/" + totalCount + ")");
        }
    }

}
