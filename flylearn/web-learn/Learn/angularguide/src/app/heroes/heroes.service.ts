import { Injectable, Inject } from '@angular/core';
import { Http, Headers } from "@angular/http";
import { UUID } from 'angular2-uuid';

import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';

import { Hero } from "../domain/Hero"

@Injectable()
export class HeroesService {
  private api_url = "http://localhost:3000/heroes";
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http, 
              @Inject("messageService")private messageService) { }

  // GET getAllHero 获取所有的英雄信息
  getAllHero(): Observable<Hero[]>{
    const url = `${this.api_url}`;
    return this.http.get(url)
                  .map(res =>{
                     let result = res.json() as Hero[];
                     this.log("获取所有英雄数据！");                         
                     return result;
                    })
                  .catch(this.handleError);
  }
  // GET getHeroById 根据ID获取英雄信息
  getHeroById(id:string):Observable<Hero>{
    const url = `${this.api_url}/?id=${id}`;
    return this.http.get(url).map(res => {
      let heroes = res.json() as Hero[];
      this.log("获取ID为"+id+"的英雄详细信息！");      
      return (heroes.length > 0)?heroes[0]:null;
    })
  }
  // 修改英雄信息
  updateHeroById(hero:Hero):void{
    const url = `${this.api_url}/${hero.id}`;
    // map返回数据，然后这种修改类型的应该使用subscribe和mapto
    this.http.put(url, hero,  {headers: this.headers})
             .mapTo(hero)
             .subscribe(res => {
                this.log("修改ID为"+hero.id+"的英雄姓名为："+hero.name+"！");      
              });
  }
  // 添加新英雄
  saveNewHero(heroName:string):Observable<Hero>{
    const url = `${this.api_url}`;
    let hero = {
      id:UUID.UUID,
      name:heroName
    };
    return this.http.post(url,hero,{headers:this.headers})
             .map(res =>{
              let resHero = res.json() as Hero;
              this.log("新增ID为"+resHero.id+",姓名为："+resHero.name+"的英雄！");
              return resHero;
             });
  }
  // 删除某个英雄
  deleteHero(hero:Hero):void{
    const url = `${this.api_url}/${hero.id}`;
    this.http.delete(url,{headers:this.headers})
             .subscribe(() => {
               this.log("删除ID为"+hero.id+",姓名为："+hero.name+"的英雄！");
             });
  }
  // 根据名字搜索
  serachByName(term:string):Observable<Hero[]>{
    const url = `${this.api_url}/?name=${term}`;
    return this.http.get(url).map(res =>{
      return res.json() as Hero[];
    });
  }

  private handleError(error:Response){
    console.log("heroserviceError:",error);
    return Observable.throw(error.json || 'Server error');
  }

  // 封装log
  private log(message: string) {
    this.messageService.add('HeroService: ' + message);
  }
}
