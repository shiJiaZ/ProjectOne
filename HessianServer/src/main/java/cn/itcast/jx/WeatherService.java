package cn.itcast.jx;

public class WeatherService implements IWeatherService{

	@Override
	public String findWeatherByCityName(String cityName) {
		if(cityName.equals("上海")){
			System.out.println("上海今天的天气:下美女");
			return "下美女";
		}else{
			System.out.println("这个城市的的天气:下刀");
			return "下刀";
		}
		
	}
	
	
	
}
