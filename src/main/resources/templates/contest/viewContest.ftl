<#import "../_layout.ftl" as layout />
<!DOCTYPE html>
<meta charset="UTF-8">
<@layout.header>
    <div style="width: 75%; margin: auto">
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap;">
            <h2 class="subtitle is-2">
                ${contest.name}
            </h2>
        </div>
    </div>
    <div>
        <div class="card">
            <div class="card-header">
                <div>
                    ${contest.description}
                </div>
            </div>
            <div class="card-content">
                <#list allProblem as problem>
                    <div class="card">
                        <div class="card-content">
                            <div class="media">
                                <div class="media-left">
                                    <figure class="image is-48x48">
                                        <img src="https://bulma.io/images/placeholders/96x96.png"
                                             alt="Placeholder image">
                                    </figure>
                                </div>
                                <div class="media-content">
                                    <p class="title is-4"><a href="http://localhost:8080/problem/${problem.slug}">${problem.name}</a></p>
                                    <p class="subtitle is-6">${problem.slug}</p>
                                </div>
                            </div>

                            <div class="content">
                                <div>
                                    Difficulté : ${problem.difficulty}
                                </div>
                                <br>
                                <#list problem.keywords as tag>
                                    <div>tag</div>
                                </#list>
                                <br>
                                <div>${problem.author}</div>
                                <#--                            <time datetime="2016-1-1">11:09 PM - 1 Jan 2016</time>-->
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</@layout.header>