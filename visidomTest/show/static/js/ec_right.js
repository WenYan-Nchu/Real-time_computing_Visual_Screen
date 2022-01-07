var ec_right = echarts.init(document.getElementById('r1'),"dark");
var ec_right_option = {
  title: {
    text: '出版社TOP10',
    x:'right'
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
    type: 'value',
    boundaryGap: [0, 0.01]
  },
  yAxis: {
    type: 'category',
    data: [],
    inverse:true
  },
  series: [
    {
      type: 'bar',
      data: [],
      itemStyle: {
            normal: {
            	color: new echarts.graphic.LinearGradient(
            		0,0,1,0,
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
ec_right.setOption(ec_right_option)
