from math import ceil

from flask import Flask, render_template
from flask import request,jsonify
import utils
app = Flask(__name__)

r=utils.Redis.get_conn()
st=[0]

@app.route("/")
def hello_world():
    return render_template("index.html")


@app.route("/c1")
def get_c1_data():
    tp = utils.Redis.get_str(r,"totalPrice").split(".")[0]
    tc = utils.Redis.get_str(r,"totalCount")
    cn = utils.Redis.get_sn(r,"consumerNum")
    vec = int(float(tc))-st[0]
    st[0] = int(float(tc))
    return jsonify({"vec":vec,"tp":tp,"tc":tc,"cn":cn})

@app.route("/c2")
def get_c2_data():
    res=[]
    oris=utils.Redis.get_place(r,"placeNum")
    for ori in oris:
        res.append({"name":ori[0],"value":int(ori[1])})
    return jsonify({"data":res})


@app.route("/l1")
def get_l1_data():
    res1=[]
    res2=[]
    oris = utils.Redis.get_rank(r, "bookRank")
    for ori in oris:
        res1.append(ori[0][0:9])
        res2.append(int(ori[1]))
    # print({"book":res1,"data":res2})
    return jsonify({"book":res1,"data":res2})


@app.route("/l2")
def get_l2_data():
    res1=[]
    res2=[]
    oris = utils.Redis.get_rank(r, "typeRank")
    for ori in oris:
        res1.append(ori[0])
        res2.append(int(ori[1]))
    return jsonify({"type":res1,"data":res2})


@app.route("/r1")
def get_r1_data():
    res1=[]
    res2=[]
    oris = utils.Redis.get_rank(r, "pressRank")
    for ori in oris:
        res1.append(ori[0])
        res2.append(int(ori[1]))
    # print({"book":res1,"data":res2})
    return jsonify({"press":res1,"data":res2})

@app.route("/r2")
def get_r2_data():
    res=[]
    oris = utils.Redis.get_mof(r, "mofNum")
    for ori in oris:
        key=ori
        value=int(oris[ori])
        res.append({"value":value,"name":key})
    return jsonify({"data":res})



@app.route("/time")
def get_time():
    return utils.get_time()



if __name__ == '__main__':
    app.run()
