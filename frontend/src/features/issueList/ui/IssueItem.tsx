import IconInfo from '@/assets/icon_info.svg?react';
import IconMilestone from '@/assets/icon_milestone.svg?react';
import type { Issue } from '@/entities/issue/issue';
import { Avatar, AvatarFallback, AvatarImage } from '@/shared/ui/avatar';
import { Badge } from '@/shared/ui/badge';
import { formatDistanceToNow } from 'date-fns';
import { ko } from 'date-fns/locale/ko';
import { Checkbox } from '../shared/CheckBox';

interface IssueItemProps {
	issue: Issue;
}

export function IssueItem({ issue }: IssueItemProps) {
	const when = formatDistanceToNow(new Date(issue.createdAt), {
		addSuffix: true,
		locale: ko,
	}); // e.g. "8분 전"

	return (
		<div className='flex w-full items-start py-4'>
			{/* 1. 체크박스 영역 */}
			<div className='flex-none px-8 h-8 flex items-center'>
				<Checkbox />
			</div>

			{/* 2. 정보 영역 */}
			<div className='flex-1 flex flex-col justify-center gap-2'>
				{/* 첫 번째 줄: 아이콘, 제목, 라벨 */}
				<div className='flex items-center gap-2'>
					<IconInfo />
					<span className='font-available-medium-20 text-[var(--neutral-text-strong)]'>
						{issue.title}
					</span>
					{issue.labels.map((lbl) => (
						<Badge key={lbl.id} color={lbl.color}>
							{lbl.name}
						</Badge>
					))}
				</div>

				{/* 두 번째 줄: 메타 정보 */}
				<div className='flex flex-wrap items-center gap-4 font-display-medium-16 text-[var(--neutral-text-weak)]'>
					<span>#{issue.id}</span>
					<span>
						{when}, {issue.author.username}님에 의해 작성되었습니다
					</span>
					{issue.milestone && (
						<span className='flex items-center gap-1'>
							<IconMilestone />
							<span>{issue.milestone.title}</span>
						</span>
					)}
				</div>
			</div>

			{/* 3. 프로필 아이콘 영역 (겹쳐서) */}
			<div className='flex-none px-4'>
				<div className='flex -space-x-2'>
					{/* 여러명일 때 map; here single author */}
					<Avatar className='z-10 border-2 border-white'>
						<AvatarImage
							src={issue.author.imageUrl}
							alt={issue.author.username}
						/>
						<AvatarFallback>
							{issue.author.username[0].toUpperCase()}
						</AvatarFallback>
					</Avatar>
				</div>
			</div>
		</div>
	);
}
