var stompClient = null;
var token = null;

function setConnected(connected) {
  $('#connect').prop('disabled', connected);
  $('#disconnect').prop('disabled', !connected);
  if (connected) {
    $('#conversation').show();
  } else {
    $('#conversation').hide();
  }
  $('#greetings').html('');
}

function connect() {
  alert('hi');
  var socket = new SockJS('/gs-guide-websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', function (greeting) {
      showGreeting(JSON.parse(greeting.body).content);
    });
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log('Disconnected');
}

function sendName() {
  stompClient.send('/app/hello', {}, JSON.stringify({ name: $('#name').val() }));
}

function showGreeting(message) {
  $('#greetings').append('<tr><td>' + message + '</td></tr>');
}

/**
 * 세션 확인 후 소켓 시작
 */
function appStart() {
  token = window.localStorage.getItem('token');

  fetch('/api/user', {
    method: 'GET',
    headers: {
      // 헤더 조작
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token,
    },
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.nickname) {
        document.getElementById('user-name').innerText = data.nickname;
        // 소켓 통신 시작점
      } else {
        alert('로그인이 필요합니다.');
        location.href = '/';
      }
    });
}

/**
 * 로그아웃
 */
function logout() {
  window.localStorage.removeItem('token');
  location.href = '/';
}

/**
 * 페이지를 닫을 경우 토큰 삭제
 */
window.addEventListener('beforeunload', function (event) {
  window.localStorage.removeItem('token');
});

appStart();

$(function () {
  $('form').on('submit', function (e) {
    e.preventDefault();
  });
  $('#connect').click(function () {
    connect();
  });
  $('#disconnect').click(function () {
    disconnect();
  });
  $('#send').click(function () {
    sendName();
  });
});
