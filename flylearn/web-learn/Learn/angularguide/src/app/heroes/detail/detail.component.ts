import { Component, OnInit,Input, Inject } from '@angular/core';

import { Hero } from "../../domain/Hero";
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
  @Input() hero:Hero;

  constructor(private route:ActivatedRoute,
              private location:Location,
              @Inject("heroesService")private heroesService) { 

              }

  ngOnInit() {
    this.getHero();
  }

  getHero(): void {
    // 路由信息的静态快照
    const id = +this.route.snapshot.paramMap.get('id');
    this.heroesService.getHeroById(id)
      .subscribe(hero => this.hero = hero);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void{
    this.heroesService.updateHeroById(this.hero);
  }
}
