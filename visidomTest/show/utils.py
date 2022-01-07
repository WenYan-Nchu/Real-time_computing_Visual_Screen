import time
import redis


def get_time():
    time_str = time.strftime("%Y{}%m{}%d{} %X")
    return time_str.format("年","月","日")


class Redis:
    @staticmethod
    def get_conn():
        ip = '127.0.0.1'
        # ip = '192.168.17.140'
        r = redis.Redis(host=ip, port=6379, db=0,decode_responses=True)  # redis默认连接db0
        return r

    @staticmethod
    def get_str(r,key):
        return r.get(key)

    @staticmethod
    def get_sn(r,key):
        return r.scard(key)

    @staticmethod
    def get_rank(r,key):
        return r.zrevrange(key,start=0,end=9,withscores=True)

    @staticmethod
    def get_place(r,key):
        return r.zrange(key,0,-1,withscores=True)

    @staticmethod
    def get_mof(r, key):
        return r.hgetall(key)



if __name__=="__main__":
    # print(get_time())
    r=Redis.get_conn()
    # res = []
    # print(Redis.get_str(r,"title"))
    # print(Redis.get_str(r,"totalPrice").split(".")[0])
    # print(Redis.get_sn(r, "consumerNum"))
    # # list,tuple
    # print(Redis.get_rank(r, "bookRank"))
    # print(Redis.get_rank(r, "pressRank")[0][0])
    # oris=Redis.get_place(r,'placeNum')
    # for ori in oris:
        # res.append({"name":ori[0],"value":int(ori[1])})
    # print(res)
    #'微信付款': '807', '支付宝付款': '497', '货到付款': '282'
    re=Redis.get_mof(r, "mofNum")
    for i in re:
        print(re[i])
    print(int(re['微信付款'])-int(re['支付宝付款']))

