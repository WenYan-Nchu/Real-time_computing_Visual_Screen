var ec_left2 = echarts.init(document.getElementById('l2'),"dark");
var ec_left2_option = {
  title: {
    text: '类型TOP10'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {},
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
  	type: 'category',
    data: [],
//  inverse:true,
    axisLabel: {
						interval: 0,    //强制文字产生间隔
						rotate: -45,     //文字旋转45°
						textStyle: {    //文字样式
								     color: "white",
						            fontSize: 12,
						            fontFamily: '宋体'
						            }
					   },

  },
  yAxis: {
  	type: 'value',
    boundaryGap: [0, 0.01]
  },
  series: [
    {
      type: 'bar',
      data: [],
      itemStyle: {
            normal: {
            	color: new echarts.graphic.LinearGradient(
//          		右/下/左/上
            		0,1,0,0,
            		[
            		{offset: 0, color: '#0C7BB3'},
//                  {offset: 0.5, color: '#12e78c'},
                    {offset: 1, color: '#F2BAE8'}
            		]
            	)
            }
       }
    }
  ]
};
ec_left2.setOption(ec_left2_option)
