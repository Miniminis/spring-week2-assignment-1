//TODO
// 1. Read Collection - GET /tasks => 완료
// 2. Read Item - GET /tasks/
// 3. Create - POST /tasks => 완료
// 4. Update - PUT/PATCH /tasks
//버튼 추가 - (조회)수정 / (수정시)확인, 취소
// 5. DELETE - DELETE /tasks
//버튼 추가 - (조회)완료

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.models.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// 짝 프로그래밍
// 일정한 시간을 두고 타이머 3분으로 맞춰놓고, 번갈아가면서

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    private List<Task> tasks = new ArrayList<>();
    private Long newId = 0L;

    //@RequestMapping(path = "", method = RequestMethod.GET)
    @GetMapping //훨씬 더 수월함.
    public List<Task> list() {
        return tasks;
    }

    //@RequestMapping(path = "", method = RequestMethod.POST)
    @PostMapping
    public Task create(@RequestBody Task task) {
        //String body = getBody(exchange);

        //String title = "test";
        //Task task = toTask(body);
        //Task task = new Task();
        task.setId(generate());
        //task.setTitle(title);
        tasks.add(task);

        return task;
    }

    /**
     * 1. 이해
     * 우리가 풀어야 될 문제가 무엇인지?
     *   * List<Task>에서 주어진 Task로 찾아야 한다.
     *
     * 우리가 알고있는 사실들은 어떤 것들이 있는지
     *   * List 타입이다.
     *   * 주어진 Task에 id가 있다.
     *
     * 2. 계획
     * for(int i; i < List.size; i++) {
     *     List.get(i).getId() == Task.getId();
     * }
     *
     * List 해시맵으로 변경한다.
     * 해시맵에서 get한다.
     *
     * List를 stream으로 변경한 후 find를 사용한다.
     *
     * indexOf를 사용하자
     /

    /**
     * 1. 이해
     * 우리가 풀어야 될 문제가 무엇인지?
     *   - 할 일의 텍스트를 수정해야 한다.
     *
     * 우리가 알고있는 사실들은 어떤 것들이 있는지
     *   - 저장되어 있는 할 일 목록이 있다. 할 일 목록은 List<Task>다.
     *   - Spring Annotation을 활용할 수 있다.
     *   - Task가 이미 정의되어 있다.
     *   - 요청으로는 Task를 요청한다.
     *   - 응답으로는 수정된 Task를 응답한다.
     *   - Task의 setTitle 메서드로 타이틀을 수정할 수 있다.
     *   - HTTP Method는 PATCH로 요청이 온다.
     *
     * 우리가 아직 모르는 것들
     *   - 사용자가 입력하는 데이터는 어떤 모양인가?
     *   - 어떤 Task를 수정할지 어떻게 알 수 있을까?
     *     - Task에 id를 포함해서 전송해야 한다.
     *
     * 비슷한 문제를 문제를 풀어본 적이 있는지?
     *   - 회사에서 데이터를 수정하는 일을 많이 해봤다.
     *
     * 우리가 문제를 풀 때 어떤 조건들이 있는지?
     *   - 수정할 할 일을 찾을 수 없으면 수정할 수 없다.
     *   - 빈 텍스트로 수정할 수 없다.
     *
     * 2. 계획
     * 사용자의 요청에 대해서 제약(빈 텍스트로 수정할 수 없다, 찾을 수 없으면 수정할 수 없다)을 건다.
     * 텍스트를 서버로 넘기는 작업
     * 받은 텍스트를 기존 텍스트를 대체한다.
     * 대체한 값을 응답한다.
     *
     * Task를 받는다.
     * 우리가 가진 할 일 목록에서, Task를 찾아요.
     *   * 할 일 목록에서 어떻게 찾는가? => indexOf()찾는다.
     * 찾은 Task를 수정해요.
     * 수정된 Task를 응답한다.
     *
     * 3. 실행
     * 4. 회고
     * 어떻게 하면 더 효과적으로 해결할 수 있었을까?
     *   찾는 부분을 분리하면 어떨까?
     *   indexOf를 좀 더 정확하게 파악하고 했으면 좋을 것 같다.
     *   equals에 대해서 더 정확하게 알고 했다면 더 좋았을 것 같다.
     * 어떻게 하면 더 효율적으로 해결할 수 있었을까?
     * 여기서 풀었던 방법으로 다른 곳에도 사용할 수 있을까?
     *   찾는 부분을 잘 분리하면 삭제할 때도 쓸 수 있지 않을까?
     *   할 일 목록 중에 하나만 찾아서 반환하는 곳에도 쓸 수 있을 것 같다.
     */
    @PatchMapping
    public Task update(@RequestBody Task task){
        // * Task를 받는다.
        // * 우리가 가진 할 일 목록에서, Task를 찾아요
        //   * 할 일 목록에서 어떻게 찾는가? => indexOf()로 찾는
        int taskIndex = tasks.indexOf(task);
        // TODO: 찾을 수 없었을 때 어떻게 할 것인가?
        Task foundTask = tasks.get(taskIndex);
        // * 찾은 Task를 수정해요.
        foundTask.setTitle(task.getTitle());
        // * 수정된 Task를 응답한다.
        return foundTask;
    }

    private Long generate(){
        newId += 1;
        return newId;
    }
}
