import { Component, OnInit, Inject } from '@angular/core';

// npm i --save angular2-uuid
import { UUID } from 'angular2-uuid';

import { Hero } from '../domain/Hero'

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
export class HeroesComponent implements OnInit {
  heroes : Hero[]; 

  constructor(@Inject("heroesService")private heroesService) {
      this.heroesService.getAllHero().subscribe((heroes:Hero[]) => {
          this.heroes = [...heroes];
      });
   }

  ngOnInit() {
  }

  // 添加英雄
  add(heroName:string){
    heroName = heroName.trim();
    if (!heroName) return;
    this.heroesService.saveNewHero(heroName)
                      .subscribe(hero => {
                        this.heroes = [...this.heroes, hero];
                      });
  }
  // 删除英雄
  delete(hero:Hero){
    const i = this.heroes.indexOf(hero);
    this.heroesService.deleteHero(hero);
    this.heroes = [
      ...this.heroes.slice(0,i),
      ...this.heroes.slice(i+1)
    ]
  }

}
